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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        @RequestParam(name = "size", defaultValue = "15") int size
    ) {
        int minPageLimit = Math.max(0, page);
        Page<UserInfoResponse> userPage = adminService.findAll(PageRequest.of(minPageLimit, size));
//        model.addAttribute("userPage", userPage);
//        return "admin/user-info";
        return ResponseEntity.ok(userPage);
    }

    @PatchMapping("/user-info/{id}/edit")
    public ResponseEntity<String> editUserInfo(
        @PathVariable("id") Long id,
        @RequestBody UserInfoUpdateRequest request,
        RedirectAttributes redirectAttributes) {

        String message = null;

        try {
            adminService.updateUserInfo(id, request);
            message =  "유저 정보가 성공적으로 수정되었습니다.";
            return ResponseEntity.ok(message);
        } catch (NotFoundException e) {
            message =  e.getMessage();
            return ResponseEntity.status(403).body(message);
        } catch (Exception e) {
            message = "알 수 없는 이유로 취소되었습니다.";
        }

//        return "redirect:/admin/user-info";
        return ResponseEntity.status(500).body(message);
    }

    @DeleteMapping("/user-info/{id}/delete")
    public ResponseEntity<String> deleteUserInfo(
        @PathVariable("id") Long id,
        RedirectAttributes redirectAttributes
    ) {
        String message = null;
        try {
            adminService.deleteById(id);
            message =  "유저를 탈퇴처리 하였습니다.";
            return ResponseEntity.ok(message);
        } catch ( NotFoundException e ) {
            message =  e.getMessage();
            return ResponseEntity.status(403).body(message);
        } catch (Exception e) {
            message = "알 수 없는 이유로 취소되었습니다.";
        }
//        return "redirect:/admin/user-info";
        return ResponseEntity.status(500).body(message);
    }

    @GetMapping("/statistic")
    public ResponseEntity<Map<String, List<?>>> statistic() {
        List<CountriesStatisticResponse> countriesStatisticResponses = adminService.getCountriesStatistics();
        List<MonthlyStatisticResponse> monthlyStatisticResponses = adminService.getMonthStatistics();
//        model.addAttribute("countriesStatisticResponses", countriesStatisticResponses);
//        model.addAttribute("monthlyStatisticResponses", monthlyStatisticResponses);
        Map<String, List<?>> statistics = new HashMap<>();
        statistics.put("countries", countriesStatisticResponses);
        statistics.put("monthly", monthlyStatisticResponses);

//        return "admin/statistic";
        return ResponseEntity.ok(statistics);
    }

    @ResponseBody
    @GetMapping("/valid-email")
    public ResponseEntity<Map<String, Boolean>> validateEmail(@RequestParam("email") String email) {
        boolean isDuplicatedEmail = adminService.isDuplicatedEmail(email);
//        return Collections.singletonMap("email", isDuplicatedEmail);
        return ResponseEntity.ok(isDuplicatedEmail ? Collections.singletonMap("duplicated", true) : Collections.emptyMap());
    }

    @ResponseBody
    @GetMapping("/valid-name")
    public ResponseEntity<Map<String, Boolean>> validateName(@RequestParam("name") String name) {
        boolean isDuplicatedName = adminService.isDuplicatedUsername(name);
//        return Collections.singletonMap("name", isDuplicatedName);
        return ResponseEntity.ok(isDuplicatedName ? Collections.singletonMap("duplicated", true) : Collections.emptyMap());
    }

}
