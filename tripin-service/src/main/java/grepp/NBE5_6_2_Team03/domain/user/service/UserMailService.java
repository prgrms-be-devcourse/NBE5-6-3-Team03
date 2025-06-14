package grepp.NBE5_6_2_Team03.domain.user.service;

import grepp.NBE5_6_2_Team03.domain.user.User;
import grepp.NBE5_6_2_Team03.domain.user.mail.CodeType;
import grepp.NBE5_6_2_Team03.domain.user.repository.UserRepository;
import grepp.NBE5_6_2_Team03.global.exception.Message;
import grepp.NBE5_6_2_Team03.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserMailService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final StringRedisTemplate redisTemplate;

    @Transactional
    public Map<String, Boolean> sendTemporaryPassword(CodeType codeType, String email) {
        String key = "tempPw:" + email;
        String value = "requested";

        if(redisTemplate.hasKey(key)){
            return Collections.singletonMap("success", false);
        }

        redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(5));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(Message.USER_NOT_FOUND));

        String code = mailService.sendCodeToEmail(codeType, email);
        String encodedPassword = passwordEncoder.encode(code);

        user.modifyPassword(encodedPassword);
        user.updateIsLocked(false);
        return Collections.singletonMap("success", true);
    }
}
