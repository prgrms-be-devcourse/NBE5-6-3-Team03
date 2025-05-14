package grepp.NBE5_6_2_Team03.api.controller.user;

import grepp.NBE5_6_2_Team03.api.controller.user.dto.request.UserSignUpRequest;
import grepp.NBE5_6_2_Team03.api.controller.user.dto.request.userEditRequest;
import grepp.NBE5_6_2_Team03.domain.user.CustomUserDetails;
import grepp.NBE5_6_2_Team03.domain.user.User;
import grepp.NBE5_6_2_Team03.domain.user.command.UserCreateCommand;
import grepp.NBE5_6_2_Team03.domain.user.command.UserEditCommand;
import grepp.NBE5_6_2_Team03.domain.user.file.FileStore;
import grepp.NBE5_6_2_Team03.domain.user.file.UploadFile;
import grepp.NBE5_6_2_Team03.domain.user.service.UserService;
import grepp.NBE5_6_2_Team03.global.config.security.SecurityContextUpdater;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.Map;

@RequestMapping("/users")
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final FileStore fileStore;
    private final SecurityContextUpdater securityContextUpdater;

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

        UserCreateCommand command = UserCreateCommand.from(request);
        userService.signup(command);
        return "redirect:/";
    }

    @GetMapping("/home")
    public String userHome(@AuthenticationPrincipal CustomUserDetails user, Model model){
        model.addAttribute("username", user.getUsername());
        return "user/home";
    }

    @GetMapping("/my-page")
    public String myPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) throws MalformedURLException {
        User user = userDetails.getUser();
        model.addAttribute("user", user);
        return "user/my-page";
    }

    @PostMapping("/edit")
    public String editUser(@AuthenticationPrincipal CustomUserDetails userDetails,
                           @ModelAttribute userEditRequest request) throws IOException {

        UploadFile uploadFile = fileStore.storeFile(request.getProfileImage());
        UserEditCommand userEditCommand = UserEditCommand.of(request, uploadFile);
        User updatedUser = userService.editUser(userEditCommand, userDetails.getId());
        securityContextUpdater.updateAuthentication(updatedUser, request.getRawPassword());

        return "redirect:/users/my-page";
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

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource showImage(@PathVariable("filename") String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFileDir() + filename);
    }
}
