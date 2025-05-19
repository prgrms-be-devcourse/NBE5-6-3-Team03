package grepp.NBE5_6_2_Team03.api.controller.mail;

import grepp.NBE5_6_2_Team03.api.controller.travelplan.dto.response.TravelPlanAdjustResponse;
import grepp.NBE5_6_2_Team03.domain.mail.service.MailService;
import grepp.NBE5_6_2_Team03.domain.travelplan.service.TravelPlanQueryService;
import grepp.NBE5_6_2_Team03.domain.user.CustomUserDetails;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {

    private final MailService mailService;
    private final TravelPlanQueryService travelPlanQueryService;

    @PostMapping("/send")
    public String sendSettlementMail(@RequestParam Long planId, @AuthenticationPrincipal CustomUserDetails customUser) {

        TravelPlanAdjustResponse response = travelPlanQueryService.getAdjustmentInfo(planId);
        Map<String,Object> settlementForm = Map.of("response",response);

        mailService.sendSettlementMail(
            customUser.getUser().getEmail(),
            "정산 결과 안내",
            "settlement-summary",
            settlementForm
        );
        return "redirect:/users/home";
    }
}
