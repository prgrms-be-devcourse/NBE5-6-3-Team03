package grepp.NBE5_6_2_Team03.domain.user.exception;

import grepp.NBE5_6_2_Team03.global.exception.BusinessException;

public class UserLoginException extends BusinessException {
    public UserLoginException(String message) {
        super(message);
    }
}
