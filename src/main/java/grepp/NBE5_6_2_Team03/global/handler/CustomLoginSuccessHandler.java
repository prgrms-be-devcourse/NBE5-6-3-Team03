package grepp.NBE5_6_2_Team03.global.handler;

import grepp.NBE5_6_2_Team03.domain.user.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private static final String ADMIN_REDIRECT_URL = "/admin/dashboard";
    private static final String USER_REDIRECT_URL = "/users/home";
    private static final String DEFAULT_REDIRECT_URL = "/";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String redirectUrl = DEFAULT_REDIRECT_URL;

        if (hasRole(authentication, Role.ROLE_ADMIN)) {
            redirectUrl = ADMIN_REDIRECT_URL ;
        }else{
            redirectUrl = USER_REDIRECT_URL;
        }

        response.sendRedirect(redirectUrl);

    }

    private boolean hasRole(Authentication auth, Role role) {
        return auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(role.name()));
    }
}
