package grepp.NBE5_6_2_Team03.domain.user.service;

import grepp.NBE5_6_2_Team03.domain.user.User;
import grepp.NBE5_6_2_Team03.domain.user.mail.CodeType;
import grepp.NBE5_6_2_Team03.domain.user.repository.UserRepository;
import grepp.NBE5_6_2_Team03.global.exception.Message;
import grepp.NBE5_6_2_Team03.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserMailService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MailService mailService;

    @Transactional
    public Map<String, Boolean> sendTemporaryPassword(CodeType codeType, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(Message.USER_NOT_FOUND));

        String code = mailService.sendCodeToEmail(codeType, email);
        String encodedPassword = passwordEncoder.encode(code);
        user.modifyPassword(encodedPassword);
        return Collections.singletonMap("success", true);
    }
}
