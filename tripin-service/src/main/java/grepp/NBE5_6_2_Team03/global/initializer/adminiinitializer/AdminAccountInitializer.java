package grepp.NBE5_6_2_Team03.global.initializer.adminiinitializer;

import grepp.NBE5_6_2_Team03.domain.user.Role;
import grepp.NBE5_6_2_Team03.domain.user.User;
import grepp.NBE5_6_2_Team03.domain.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminAccountInitializer {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostConstruct
    public void initAdminAccount() {
        String adminEmail = "admin@admin.com";

        if (userRepository.findByEmail(adminEmail).isPresent()) {
            return;
        }

        User admin = User.builder()
            .email(adminEmail)
            .password(bCryptPasswordEncoder.encode("admin1234"))
            .name("관리자")
            .role(Role.ROLE_ADMIN)
            .build();

        userRepository.save(admin);

    }
}
