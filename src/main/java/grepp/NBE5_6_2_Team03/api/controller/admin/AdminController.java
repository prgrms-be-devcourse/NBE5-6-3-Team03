package grepp.NBE5_6_2_Team03.api.controller.admin;

import grepp.NBE5_6_2_Team03.api.controller.admin.dto.statistic.CountriesStatisticResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.statistic.MonthlyStatisticResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.user.UserInfoResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.user.UserInfoUpdateRequest;
import grepp.NBE5_6_2_Team03.domain.admin.AdminService;
import grepp.NBE5_6_2_Team03.global.exception.NotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<Page<UserInfoResponse>> userInfos(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "16") int size
    ) {
        int minPageLimit = Math.max(0, page);
        return ResponseEntity.ok(adminService.findAll(PageRequest.of(minPageLimit, size)));
    }

    @PatchMapping("/user-info/{id}/edit")
    public ResponseEntity<Map<String, String>> editUserInfo(
        @PathVariable("id") Long id,
        @RequestBody UserInfoUpdateRequest request
    ) {

        String message = null;

        try {
            adminService.updateUserInfo(id, request);
            message =  "유저 정보가 성공적으로 수정되었습니다.";
            return ResponseEntity.ok(getMessage(message));
        } catch (NotFoundException e) {
            message =  e.getMessage();
            return ResponseEntity.status(403).body(getMessage(message));
        } catch (Exception e) {
            message = "알 수 없는 이유로 취소되었습니다.";
        }
        return ResponseEntity.status(500).body(getMessage(message));
    }

    @DeleteMapping("/user-info/{id}/delete")
    public ResponseEntity<Map<String, String>> deleteUserInfo(
        @PathVariable("id") Long id
    ) {
        String message = null;
        try {
            adminService.deleteById(id);
            message =  "유저를 탈퇴처리 하였습니다.";
            return ResponseEntity.ok(getMessage(message));
        } catch ( NotFoundException e ) {
            message =  e.getMessage();
            return ResponseEntity.status(403).body(getMessage(message));
        } catch (Exception e) {
            message = "알 수 없는 이유로 취소되었습니다.";
        }
        return ResponseEntity.status(500).body(getMessage(message));
    }

    private static Map<String, String> getMessage(String message) {
        return Map.of(
            "message", message,
            "redirect", "/admin/user-info"
        );
    }

    @GetMapping("/statistic")
    public ResponseEntity<Map<String, List<?>>> statistic() {
        List<CountriesStatisticResponse> countriesStatisticResponses = adminService.getCountriesStatistics();
        List<MonthlyStatisticResponse> monthlyStatisticResponses = adminService.getMonthStatistics();
        Map<String, List<?>> statistics = new HashMap<>() {
            {
                put("countries", countriesStatisticResponses);
                put("monthly", monthlyStatisticResponses);
            }
        };

        log.info(statistics.toString());

        return ResponseEntity.ok(statistics);
    }

    @GetMapping("/valid-email")
    public ResponseEntity<Map<String, Boolean>> validateEmail(@RequestParam("email") String email) {
        boolean isDuplicatedEmail = adminService.isDuplicatedEmail(email);
        return ResponseEntity.ok(isDuplicatedEmail ? Collections.singletonMap("duplicated", true) : Collections.emptyMap());
    }

    @GetMapping("/valid-name")
    public ResponseEntity<Map<String, Boolean>> validateName(@RequestParam("name") String name) {
        boolean isDuplicatedName = adminService.isDuplicatedUsername(name);
        return ResponseEntity.ok(isDuplicatedName ? Collections.singletonMap("duplicated", true) : Collections.emptyMap());
    }

}
