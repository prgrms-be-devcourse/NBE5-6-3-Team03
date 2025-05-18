package grepp.NBE5_6_2_Team03.global.advice;

import grepp.NBE5_6_2_Team03.domain.user.exception.InvalidPasswordException;
import grepp.NBE5_6_2_Team03.domain.user.exception.UserSignUpException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(UserSignUpException.class)
    public String userSignUpExceptionHandler(UserSignUpException e){
        return "redirect:/users/sign-up";
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public String userModifyExceptionHandler(InvalidPasswordException e, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/users/my-page";
    }
}
