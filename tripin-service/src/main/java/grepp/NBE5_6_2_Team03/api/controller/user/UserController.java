package grepp.NBE5_6_2_Team03.api.controller.user;

import grepp.NBE5_6_2_Team03.api.controller.user.dto.request.UserEditRequest;
import grepp.NBE5_6_2_Team03.api.controller.user.dto.request.UserSignUpRequest;
import grepp.NBE5_6_2_Team03.api.controller.user.dto.response.UserMyPageResponse;
import grepp.NBE5_6_2_Team03.domain.user.CustomUserDetails;
import grepp.NBE5_6_2_Team03.domain.user.service.UserService;
import grepp.NBE5_6_2_Team03.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.Map;

@RequestMapping("/users")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ApiResponse<Long> signUp(@RequestBody @Valid UserSignUpRequest request) {
        return new ApiResponse<>(HttpStatus.CREATED.name(), "성공", userService.signup(request));
    }

    @GetMapping("/my-page")
    public ApiResponse<UserMyPageResponse> getMyPage(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ApiResponse.success(userService.getMyProfile(userDetails.getId()));
    }

    // 동작 X
    @PostMapping("/edit")
    public ApiResponse<Void> modifyProfile(@RequestBody @Valid UserEditRequest request,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) throws IOException {
        userService.updateProfile(request, userDetails.getId());
        return ApiResponse.noContent();
    }

    @PostMapping("{user-id}")
    public ApiResponse<Void> deleteUser(@PathVariable("user-id") Long userId) {
        userService.deleteByUserId(userId);
        return ApiResponse.noContent();
    }

    @GetMapping("/check-email")
    public Map<String, Boolean> checkEmail(@RequestParam("email") String email) {
        boolean available = userService.isNotDuplicatedEmail(email);
        return Collections.singletonMap("available", available);
    }

    @GetMapping("/check-name")
    public Map<String, Boolean> checkName(@RequestParam("name") String name) {
        boolean available = userService.isNotDuplicatedName(name);
        return Collections.singletonMap("available", available);
    }

    @GetMapping("/images/{filename}")
    public Resource showImage(@PathVariable("filename") String filename) throws MalformedURLException {
        String fileDir = userService.getFileDir();
        return new UrlResource("file:" + fileDir + filename);
    }
}
