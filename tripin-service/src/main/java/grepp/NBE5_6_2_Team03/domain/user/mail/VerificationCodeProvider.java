package grepp.NBE5_6_2_Team03.domain.user.mail;

import grepp.NBE5_6_2_Team03.global.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class VerificationCodeProvider implements CodeProvidable {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int CODE_LENGTH = 10;

    @Override
    public boolean isSupport(CodeType codeType) {
        return codeType.isVerificationCode();
    }

    @Override
    public String provide(CodeType codeType) {
        if(codeType.isNotVerificationCode()){
            throw new BusinessException("CODE_TYPE 예외");
        }

        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomNum = RANDOM.nextInt(CODE_LENGTH);
            sb.append(randomNum);
        }
        return sb.toString();
    }
}
