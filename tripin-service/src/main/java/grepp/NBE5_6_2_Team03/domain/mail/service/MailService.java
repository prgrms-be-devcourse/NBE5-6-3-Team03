package grepp.NBE5_6_2_Team03.domain.mail.service;

import grepp.NBE5_6_2_Team03.domain.user.code.CodeFinder;
import grepp.NBE5_6_2_Team03.domain.user.code.CodeType;
import grepp.NBE5_6_2_Team03.global.exception.BusinessException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender javaMailSender;
    private final CodeFinder codeFinder;

    public MailService(JavaMailSender javaMailSender, CodeFinder codeFinder) {
        this.javaMailSender = javaMailSender;
        this.codeFinder = codeFinder;
    }

    public String sendCodeToEmail(CodeType codeType, String email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        try {
            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject(codeType.getDescription());

            String code = codeFinder.findCodeFrom(codeType);
            simpleMailMessage.setText(code);

            javaMailSender.send(simpleMailMessage);
            return code;
        } catch (Exception e) {
            throw new BusinessException("메일 전송 예외");
        }
    }
}
