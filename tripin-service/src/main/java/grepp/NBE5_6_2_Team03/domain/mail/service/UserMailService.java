package grepp.NBE5_6_2_Team03.domain.mail.service;

import grepp.NBE5_6_2_Team03.domain.user.User;
import grepp.NBE5_6_2_Team03.domain.user.code.CodeType;
import grepp.NBE5_6_2_Team03.domain.user.repository.UserRepository;
import grepp.NBE5_6_2_Team03.global.exception.NotFoundException;
import grepp.NBE5_6_2_Team03.global.message.ExceptionMessage;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserMailService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final StringRedisTemplate redisTemplate;
    private final MailServiceClient mailServiceClient;
    private final MailService mailService;

    @Transactional
    public void sendTemporaryPassword(CodeType codeType,String email) {
        String key = "tempPw:" + email;
        String value = "requested";

        if (redisTemplate.hasKey(key)) {
            return;
        }

        redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(5));

        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new NotFoundException(ExceptionMessage.USER_NOT_FOUND));

//        CodeMailResponse response = mailServiceClient.sendCode(email, CodeType.PASSWORD).block(); // 메일 서버에서 발송
//        boolean result = Optional.ofNullable(response)
//            .map(CodeMailResponse::code)
//            .map(password -> {
//                user.modifyPassword(password);
//                user.updateIsLocked(false);
//                return true;
//            })
//            .orElse(false);

        String code = mailService.sendCodeToEmail(codeType, email);
        String encodedPassword = passwordEncoder.encode(code);

        user.modifyPassword(encodedPassword);
        user.updateIsLocked(false);

    }
}
