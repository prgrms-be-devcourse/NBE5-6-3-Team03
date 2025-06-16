package grepp.NBE5_6_2_Team03.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import grepp.NBE5_6_2_Team03.domain.user.CustomUserDetails;
import grepp.NBE5_6_2_Team03.domain.user.User;
import grepp.NBE5_6_2_Team03.domain.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, String> redisTemplate;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public LoginFilter(@Qualifier("redisTemplate") RedisTemplate<String, String> redisTemplate,
                       UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.redisTemplate = redisTemplate;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String email = null;
        String password = null;

        try {
            BufferedReader reader = request.getReader();
            Map<String, String> jsonMap = objectMapper.readValue(reader, Map.class);

            email = jsonMap.get("email");
            password = jsonMap.get("password");
            request.setAttribute("email", email);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read Json");
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        String key = "login:fail:" + request.getAttribute("email");
        redisTemplate.delete(key);

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        Long userId = customUserDetails.getId();
        String username = customUserDetails.getUsername();
        String email = customUserDetails.getUser().getEmail();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(userId, username, email, role, 30 * 24 * 60 * 60 * 1000L);

        response.addHeader("Authorization", "Bearer " + token);
        Map<String, Object> result = new HashMap<>();
        result.put("message", "로그인 성공");
        result.put("token", token);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {;
        String email = (String) request.getAttribute("email");

        Optional<User> user = userRepository.findByEmail(email);
        user.ifPresent(u -> updateFailCount(u, email));

        response.setStatus(401);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("failCount", getFailCount(email));
        responseBody.put("message", "Authentication failed");
        objectMapper.writeValue(response.getWriter(), responseBody);
    }

    private String getFailCount(String email) {
        String key = findKey(email);
        return redisTemplate.opsForValue().get(key);
    }

    private void updateFailCount(User u, String email) {
        String key = findKey(email);
        Long failCount = redisTemplate.opsForValue().increment(key);

        if(isFirstLoginFail(failCount)){
            redisTemplate.expire(key, Duration.ofHours(6));
        }

        if(isOverMaxFailAttempts(failCount)){
            u.updateIsLocked(true);
        }
    }

    private String findKey(String email) {
        return "login:fail:" + email;
    }

    private boolean isOverMaxFailAttempts(Long failCount) {
        return failCount >= 5;
    }

    private boolean isFirstLoginFail(Long failCount) {
        return failCount == 1;
    }
}
