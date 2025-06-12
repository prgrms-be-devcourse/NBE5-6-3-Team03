package grepp.NBE5_6_2_Team03.api.controller.admin;

import grepp.NBE5_6_2_Team03.api.controller.admin.dto.statistic.CountriesStatisticResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.statistic.MonthlyStatisticResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.user.UserInfoResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.user.UserInfoUpdateRequest;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.user.UserSearchRequest;
import grepp.NBE5_6_2_Team03.domain.admin.AdminService;
import grepp.NBE5_6_2_Team03.global.response.ApiResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/user-info")
    public ApiResponse<Page<UserInfoResponse>> userInfos(
        @ModelAttribute UserSearchRequest request
    ) {
        return ApiResponse.success(adminService.findUsersPage(request));
    }

    @PatchMapping("/user-info/{id}/edit")
    public ApiResponse<Map<String, String>> editUserInfo(
        @PathVariable("id") Long id,
        @RequestBody UserInfoUpdateRequest request
    ) {
        adminService.updateUserInfo(id, request);
        return ApiResponse.success(createSuccessMessage("유저 정보가 성공적으로 수정되었습니다."));
    }

    @DeleteMapping("/user-info/{id}/delete")
    public ApiResponse<Map<String, String>> deleteUserInfo(
        @PathVariable("id") Long id
    ) {
        adminService.deleteById(id);
        return ApiResponse.success(createSuccessMessage("유저를 탈퇴처리 하였습니다."));
    }

    @GetMapping("/statistic")
    public ApiResponse<Map<String, List<?>>> statistic() {
        List<CountriesStatisticResponse> countriesStatisticResponses = adminService.getCountriesStatistics();
        List<MonthlyStatisticResponse> monthlyStatisticResponses = adminService.getMonthStatistics();
        Map<String, List<?>> statistics = new HashMap<>() {
            {
                put("countries", countriesStatisticResponses);
                put("monthly", monthlyStatisticResponses);
            }
        };

        log.info(statistics.toString());

        return ApiResponse.success(statistics);
    }

    @GetMapping("/valid-email")
    public ApiResponse<Map<String, Boolean>> validateEmail(@RequestParam("email") String email) {
        boolean isDuplicatedEmail = adminService.isDuplicatedEmail(email);
        return ApiResponse.success(isDuplicatedEmail ? Collections.singletonMap("duplicated", true) : Collections.emptyMap());
    }

    @GetMapping("/valid-name")
    public ApiResponse<Map<String, Boolean>> validateName(@RequestParam("name") String name) {
        boolean isDuplicatedName = adminService.isDuplicatedUsername(name);
        return ApiResponse.success(isDuplicatedName ? Collections.singletonMap("duplicated", true) : Collections.emptyMap());
    }

    private Map<String, String> createSuccessMessage(String message) {
        return Map.of(
            "message", message,
            "redirect", "/admin/user-info"
        );
    }

}
