package grepp.NBE5_6_2_Team03.api.controller.admin.userinfo;

import grepp.NBE5_6_2_Team03.api.controller.admin.userinfo.dto.UserInfoResponse;
import grepp.NBE5_6_2_Team03.domain.admin.userinfo.UserInfo;
import grepp.NBE5_6_2_Team03.domain.admin.userinfo.UserInfoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class UserInfoController {

    private final UserInfoService userInfoService;

    @GetMapping("/user-info")
    public String userInfos(Model model) {
        List<UserInfoResponse> userInfos = userInfoService.findAll();
        model.addAttribute("userInfos", userInfos);
        return "admin/user-info";
    }

}
