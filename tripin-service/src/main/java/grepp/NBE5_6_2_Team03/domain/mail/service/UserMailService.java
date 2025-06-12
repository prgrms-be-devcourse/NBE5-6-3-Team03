package grepp.NBE5_6_2_Team03.domain.mail.service;

import grepp.NBE5_6_2_Team03.domain.mail.dto.CodeMailResponse;
import grepp.NBE5_6_2_Team03.domain.mail.dto.CodeType;
import grepp.NBE5_6_2_Team03.domain.user.User;
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
    private final StringRedisTemplate redisTemplate;
    private final MailServiceClient mailServiceClient;

    @Transactional
    public Map<String, Boolean> sendTemporaryPassword(String email) {
        String key = "tempPw:" + email;
        String value = "requested";

        if(redisTemplate.hasKey(key)){
            return Collections.singletonMap("success", false);
        }

        redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(5));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(Message.USER_NOT_FOUND));

        CodeMailResponse response = mailServiceClient.sendCode(email, CodeType.PASSWORD).block();

        if(response != null && response.code() != null){
            String temporaryPassword = response.code();

            user.modifyPassword(passwordEncoder.encode(temporaryPassword));
            user.updateIsLocked(false);
            userRepository.save(user);
            return Collections.singletonMap("success", true);
        }else{
            return Collections.singletonMap("success", false);
        }
    }
}
