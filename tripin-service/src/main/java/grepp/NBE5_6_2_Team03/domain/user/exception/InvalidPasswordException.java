package grepp.NBE5_6_2_Team03.domain.user.exception;

import grepp.NBE5_6_2_Team03.global.message.ExceptionMessage;

public class InvalidPasswordException extends RuntimeException{

    private final ExceptionMessage message;

    public InvalidPasswordException(ExceptionMessage message) {
        super(message.getDescription());
        this.message = message;
    }
}
