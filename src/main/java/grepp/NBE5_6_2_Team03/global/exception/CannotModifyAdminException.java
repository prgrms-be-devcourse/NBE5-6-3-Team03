package grepp.NBE5_6_2_Team03.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class CannotModifyAdminException extends BusinessException {

    public CannotModifyAdminException(Message message) {
        super(message.getDescription());
    }
}