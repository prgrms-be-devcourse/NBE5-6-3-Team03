package grepp.NBE5_6_2_Team03.api.controller.user;

import grepp.NBE5_6_2_Team03.domain.user.service.UserMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static grepp.NBE5_6_2_Team03.domain.user.mail.CodeType.*;

@RequiredArgsConstructor
@RequestMapping("/users")
@Controller
public class UserMailController {

    private final UserMailService userMailService;

    @GetMapping("/password-reset")
    public String passwordResetForm(){
        return "/user/password-reset";
    }

    @ResponseBody
    @PostMapping("/email/{email}/send-mail")
    public Map<String, Boolean> sendCodeToMail(@PathVariable("email") String email){
        return userMailService.sendTemporaryPassword(PASSWORD, email);
    }

}