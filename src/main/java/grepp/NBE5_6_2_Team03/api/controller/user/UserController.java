package grepp.NBE5_6_2_Team03.api.controller.user;

import grepp.NBE5_6_2_Team03.api.controller.user.dto.request.UserSignUpRequest;
import grepp.NBE5_6_2_Team03.api.controller.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RequestMapping("/users")
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/sign-up")
    public String signUpForm(Model model){
        model.addAttribute("userSignUpRequest", new UserSignUpRequest());
        return "/user/signup-form";
    }

    @PostMapping("/sign-up")
    public String signUp(@Valid @ModelAttribute UserSignUpRequest request, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/user/signup-form";
        }

        userService.signup(request);
        return "redirect:/";
    }

    @ResponseBody
    @GetMapping("/check-email")
    public Map<String, Boolean> checkEmail(@RequestParam String email){
        boolean exists = userService.findByEmail(email);
        return Collections.singletonMap("available", !exists);
    }

    @ResponseBody
    @GetMapping("/check-name")
    public Map<String, Boolean> checkName(@RequestParam String name){
        boolean exists = userService.findByName(name);
        return Collections.singletonMap("available", !exists);
    }
}
