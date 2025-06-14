package grepp.NBE5_6_2_Team03.domain.user.exception;

import grepp.NBE5_6_2_Team03.global.exception.BusinessException;
import grepp.NBE5_6_2_Team03.global.message.ExceptionMessage;
import grepp.NBE5_6_2_Team03.global.exception.Reason;

public class UserSignUpException extends BusinessException {
    private final Reason reason;

    public UserSignUpException(Reason reason, ExceptionMessage message) {
        super(message.getDescription());
        this.reason = reason;
    }

    public String getReason() {
        return reason.getDescription();
    }
}
