package grepp.NBE5_6_2_Team03.api.controller.mail;

import grepp.NBE5_6_2_Team03.domain.mail.service.UserMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/users")
@Controller
public class UserMailController {

    private final UserMailService userMailService;

    @GetMapping("/password-reset")
    public String passwordResetForm(){
        return "user/password-reset";
    }

    @ResponseBody
    @PostMapping("/email/{email}/send-mail")
    public Map<String, Boolean> sendCodeToMail(@PathVariable("email") String email){
        return userMailService.sendTemporaryPassword(email);
    }

}