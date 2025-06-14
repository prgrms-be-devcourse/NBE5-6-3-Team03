package grepp.NBE5_6_2_Team03.domain.user.exception;

import grepp.NBE5_6_2_Team03.global.exception.Message;

public class InvalidPasswordException extends RuntimeException{

    private final Message message;

    public InvalidPasswordException(Message message) {
        super(message.getDescription());
        this.message = message;
    }
}
