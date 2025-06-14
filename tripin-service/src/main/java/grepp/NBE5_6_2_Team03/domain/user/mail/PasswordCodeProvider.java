package grepp.NBE5_6_2_Team03.domain.user.mail;

import grepp.NBE5_6_2_Team03.global.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class PasswordCodeProvider implements CodeProvidable{
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int PASSWORD_LENGTH = 10;
    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public boolean isSupport(CodeType codeType) {
        return codeType.isPasswordCode();
    }

    @Override
    public String provide(CodeType codeType) {
        if(codeType.isNotPasswordCode()){
            throw new BusinessException("CODE_TYPE 불일치");
        }

        StringBuilder sb = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }
}
