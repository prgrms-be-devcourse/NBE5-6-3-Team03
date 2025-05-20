package grepp.NBE5_6_2_Team03.api.controller.admin;

import grepp.NBE5_6_2_Team03.api.controller.admin.dto.statistic.CountriesStatisticResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.statistic.MonthlyStatisticResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.user.UserInfoResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.user.UserInfoUpdateRequest;
import grepp.NBE5_6_2_Team03.domain.admin.AdminService;
import grepp.NBE5_6_2_Team03.global.exception.NotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/user-info")
    public String userInfos(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "15") int size,
        Model model) {
        int minPageLimit = Math.max(0, page);
        Page<UserInfoResponse> userPage = adminService.findAll(PageRequest.of(minPageLimit, size));
        model.addAttribute("userPage", userPage);
        return "admin/user-info";
    }

    @PatchMapping("/user-info/{id}/edit")
    public String editUserInfo(
        @PathVariable("id") Long id,
        UserInfoUpdateRequest request,
        RedirectAttributes redirectAttributes) {

        try {
            adminService.updateUserInfo(id, request);
            redirectAttributes.addFlashAttribute("message", "유저 정보가 성공적으로 수정되었습니다.");
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/admin/user-info";
    }

    @DeleteMapping("/user-info/{id}/delete")
    public String deleteUserInfo(
        @PathVariable("id") Long id,
        RedirectAttributes redirectAttributes
    ) {
        try {
            adminService.lockedById(id);
            redirectAttributes.addFlashAttribute("message", "유저를 탈퇴처리 하였습니다.");
        } catch ( NotFoundException e ) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/admin/user-info";
    }

    @GetMapping("/statistic")
    public String statistic(Model model) {
        List<CountriesStatisticResponse> countriesStatisticResponses = adminService.getCountriesStatistics();
        List<MonthlyStatisticResponse> monthlyStatisticResponses = adminService.getMonthStatistics();
        model.addAttribute("countriesStatisticResponses", countriesStatisticResponses);
        model.addAttribute("monthlyStatisticResponses", monthlyStatisticResponses);
        return "admin/statistic";
    }

    @ResponseBody
    @GetMapping("/valid-email")
    public Map<String, Boolean> validateEmail(@RequestParam("email") String email) {
        boolean isDuplicatedEmail = adminService.isDuplicatedEmail(email);
        return Collections.singletonMap("email", isDuplicatedEmail);
    }

    @ResponseBody
    @GetMapping("/valid-name")
    public Map<String, Boolean> validateName(@RequestParam("name") String name) {
        boolean isDuplicatedName = adminService.isDuplicatedUsername(name);
        return Collections.singletonMap("name", isDuplicatedName);
    }

}
