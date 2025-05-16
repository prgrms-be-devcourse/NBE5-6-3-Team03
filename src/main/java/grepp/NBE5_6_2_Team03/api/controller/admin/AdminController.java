package grepp.NBE5_6_2_Team03.api.controller.admin;

import grepp.NBE5_6_2_Team03.api.controller.admin.dto.user.UserInfoResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.user.UserInfoUpdateRequest;
import grepp.NBE5_6_2_Team03.domain.admin.AdminService;
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
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "15") int size,
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

        adminService.updateUserInfo(id, request);
        redirectAttributes.addFlashAttribute("message", "UserInfo updated successfully.");
        return "redirect:/admin/user-info";
    }

    @DeleteMapping("/user-info/{id}/delete")
    public String deleteUserInfo(
        @PathVariable("id") Long id,
        RedirectAttributes redirectAttributes
    ) {
        adminService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "UserInfo deleted successfully.");
        return "redirect:/admin/user-info";
    }


}
