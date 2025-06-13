package grepp.NBE5_6_2_Team03.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotFoundException extends RuntimeException{

    private final Message message;

    public NotFoundException(Message message) {
        super(message.getDescription());
        this.message = message;
    }
}
