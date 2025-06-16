package grepp.NBE5_6_2_Team03.api.controller.mail;

import grepp.NBE5_6_2_Team03.domain.mail.service.UserMailService;
import grepp.NBE5_6_2_Team03.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserMailController {

    private final UserMailService userMailService;

    @GetMapping("/password-reset")
    public ApiResponse<Void> passwordResetForm() {
        return ApiResponse.noContent();
    }

    @ResponseBody
    @PostMapping("/email/{email}/send-mail")
    public ApiResponse<Void> sendCodeToMail(@PathVariable("email") String email) {
        userMailService.sendTemporaryPassword(email);
        return ApiResponse.noContent();
    }

}