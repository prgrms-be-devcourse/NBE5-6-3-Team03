package grepp.NBE5_6_2_Team03.api.controller.admin;

import grepp.NBE5_6_2_Team03.api.controller.admin.dto.statistic.StatisticResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.user.UserDetailResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.user.UserModifyRequest;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.user.UserSearchPageResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.user.UserSearchRequest;
import grepp.NBE5_6_2_Team03.domain.admin.AdminService;
import grepp.NBE5_6_2_Team03.global.message.AdminSuccessMessage;
import grepp.NBE5_6_2_Team03.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/users")
    public ApiResponse<UserSearchPageResponse> getUsers(@RequestBody UserSearchRequest request) {
        return ApiResponse.success(adminService.findUsersPage(request));
    }

    @GetMapping("/users/{id}")
    public ApiResponse<UserDetailResponse> getUser(@PathVariable("id") Long id) {
        return ApiResponse.success(adminService.findById(id));
    }

    @PutMapping("/users/{id}")
    public ApiResponse<String> updateUserInfo(@RequestBody UserModifyRequest request, @PathVariable("id") Long id) {
        adminService.updateUserInfo(request, id);
        return ApiResponse.success(AdminSuccessMessage.USER_INFO_UPDATED.getMessage());
    }

    @DeleteMapping("/users/{id}")
    public ApiResponse<String> deleteUserInfo(
            @PathVariable("id") Long id
    ) {
        adminService.deleteUserById(id);
        return ApiResponse.noContent();
    }

    @PatchMapping("/users/{id}/lock")
    public ApiResponse<String> lockUser(
            @PathVariable("id") Long userId
    ) {
        adminService.lockUser(userId);
        return ApiResponse.success(AdminSuccessMessage.USER_LOCKED.getMessage());
    }

    @PatchMapping("/users/{id}/unlock")
    public ApiResponse<String> unlockUser(
            @PathVariable("id") Long userId
    ) {
        adminService.unlockUser(userId);
        return ApiResponse.success(AdminSuccessMessage.USER_UNLOCKED.getMessage());
    }

    @GetMapping("/statistic")
    public ApiResponse<StatisticResponse> getStatistics() {
        StatisticResponse statisticResponse = StatisticResponse
                .builder()
                .countries(adminService.getCountriesStatistics())
                .monthly(adminService.getMonthStatistics())
                .build();
        return ApiResponse.success(statisticResponse);
    }

    @GetMapping("/valid-email")
    public ApiResponse<Map<String, Boolean>> validateEmail(@RequestParam("email") String email) {
        boolean isDuplicatedEmail = adminService.isDuplicatedEmail(email);
        return ApiResponse.success(isDuplicatedEmail ?
                Collections.singletonMap("duplicated", true) : Collections.singletonMap("duplicated", false));
    }

    @GetMapping("/valid-name")
    public ApiResponse<Map<String, Boolean>> validateName(@RequestParam("name") String name) {
        boolean isDuplicatedName = adminService.isDuplicatedUsername(name);
        return ApiResponse.success(isDuplicatedName ?
                Collections.singletonMap("duplicated", true) : Collections.singletonMap("duplicated", false));
    }

}