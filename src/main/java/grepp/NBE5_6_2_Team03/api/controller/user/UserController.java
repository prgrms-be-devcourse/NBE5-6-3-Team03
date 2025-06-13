package grepp.NBE5_6_2_Team03.api.controller.user;

import grepp.NBE5_6_2_Team03.api.controller.user.dto.request.UserEditRequest;
import grepp.NBE5_6_2_Team03.api.controller.user.dto.request.UserSignUpRequest;
import grepp.NBE5_6_2_Team03.api.controller.user.dto.response.UserMyPageResponse;
import grepp.NBE5_6_2_Team03.domain.travelplan.TravelPlan;
import grepp.NBE5_6_2_Team03.domain.travelplan.service.TravelPlanService;
import grepp.NBE5_6_2_Team03.domain.user.CustomUserDetails;
import grepp.NBE5_6_2_Team03.domain.user.service.UserService;
import grepp.NBE5_6_2_Team03.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final TravelPlanService travelPlanService;

    @GetMapping("/sign-up")
    public String signUpForm(Model model) {
        model.addAttribute("userSignUpRequest", new UserSignUpRequest());
        return "/user/signup-form";
    }

    @ResponseBody
    @PostMapping("/sign-up")
    public ApiResponse<Long> signUp(@Valid @RequestBody UserSignUpRequest request) {
        return new ApiResponse<>(HttpStatus.CREATED.name(), "성공", userService.signup(request));
    }

    @GetMapping("/home")
    public String userHomeForm(@AuthenticationPrincipal CustomUserDetails user, Model model) {
        model.addAttribute("username", user.getUsername());
        List<TravelPlan> plans = travelPlanService.getPlansByUser(user.getId());
        model.addAttribute("plans", plans);
        return "plan/plan";
    }

    @GetMapping("/my-page")
    public String myPageForm(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        UserMyPageResponse userMyPageResponse = userService.getMyProfile(userDetails.getId());
        model.addAttribute("userMyPageResponse", userMyPageResponse);
        return "user/my-page";
    }

    @PostMapping("/edit")
    public String modifyProfile(@ModelAttribute @Valid UserEditRequest request, BindingResult bindingResult,
                                @AuthenticationPrincipal CustomUserDetails userDetails, RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            UserMyPageResponse userMyPageResponse = userService.getMyProfile(userDetails.getId());
            redirectAttributes.addFlashAttribute("userMyPageResponse", userMyPageResponse);
            return "redirect:/users/my-page";
        }

        UserMyPageResponse userMyPageResponse = userService.updateProfile(request, userDetails.getId());
        redirectAttributes.addFlashAttribute("userMyPageResponse", userMyPageResponse);
        return "redirect:/users/my-page";
    }

    @PostMapping("{user-id}")
    public String deleteUser(@PathVariable("user-id") Long userId) {
        userService.deleteUserBy(userId);
        return "redirect:/";
    }

    @ResponseBody
    @GetMapping("/check-email")
    public Map<String, Boolean> checkEmail(@RequestParam("email") String email) {
        boolean available = userService.isNotDuplicatedEmail(email);
        return Collections.singletonMap("available", available);
    }

    @ResponseBody
    @GetMapping("/check-name")
    public Map<String, Boolean> checkName(@RequestParam("name") String name) {
        boolean available = userService.isNotDuplicatedName(name);
        return Collections.singletonMap("available", available);
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource showImage(@PathVariable("filename") String filename) throws MalformedURLException {
        String fileDir = userService.getFileDir();
        return new UrlResource("file:" + fileDir + filename);
    }
}
