package grepp.NBE5_6_2_Team03.api.controller.user;

import grepp.NBE5_6_2_Team03.domain.user.service.UserMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;

import static grepp.NBE5_6_2_Team03.domain.user.mail.CodeType.*;

@RequiredArgsConstructor
@RequestMapping("/users")
@Controller
public class UserMailController {

    private final UserMailService userMailService;
    private final StringRedisTemplate redisTemplate;

    @GetMapping("/password-reset")
    public String passwordResetForm(){
        return "/user/password-reset";
    }

    @ResponseBody
    @PostMapping("/email/{email}/send-mail")
    public Map<String, Boolean> sendCodeToMail(@PathVariable("email") String email){
        String key = "tempPw:" + email;
        String value = "requested";

        if(redisTemplate.hasKey(key)){
            return Collections.singletonMap("success", false);
        }

        redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(5));
        return userMailService.sendTemporaryPassword(PASSWORD, email);
    }

}