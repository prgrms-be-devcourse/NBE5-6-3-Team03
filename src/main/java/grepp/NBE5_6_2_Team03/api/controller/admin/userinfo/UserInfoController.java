package grepp.NBE5_6_2_Team03.api.controller.admin.userinfo;

import grepp.NBE5_6_2_Team03.api.controller.admin.userinfo.dto.UserInfoResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.userinfo.dto.UserInfoUpdateRequest;
import grepp.NBE5_6_2_Team03.domain.admin.userinfo.UserInfo;
import grepp.NBE5_6_2_Team03.domain.admin.userinfo.UserInfoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class UserInfoController {

    private final UserInfoService userInfoService;

    @GetMapping("/user-info")
    public String userInfos(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "15") int size,
        Model model) {
        int safePage = Math.max(0, page);
        Page<UserInfoResponse> userPage = userInfoService.findAll(PageRequest.of(safePage, size));
        model.addAttribute("userPage", userPage);
        return "admin/user-info";
    }

    @PostMapping("/user-info/{id}/edit")
    public String editUserInfo(
        @PathVariable("id") Long id,
        UserInfoUpdateRequest request,
        RedirectAttributes redirectAttributes) {

        userInfoService.updateUserInfo(id, request);
        redirectAttributes.addFlashAttribute("message", "Place updated successfully.");
        return "redirect:/admin/user-info";
    }


}
