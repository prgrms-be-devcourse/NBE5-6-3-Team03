package grepp.NBE5_6_2_Team03.global.advice;

import grepp.NBE5_6_2_Team03.global.exception.UserSignUpException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(UserSignUpException.class)
    public String userSignUpExceptionHandler(UserSignUpException e){
        return "/user/signup-form";
    }
}
