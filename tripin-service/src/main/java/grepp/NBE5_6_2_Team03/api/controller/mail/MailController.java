package grepp.NBE5_6_2_Team03.api.controller.mail;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import grepp.NBE5_6_2_Team03.api.controller.travelplan.dto.response.TravelPlanAdjustResponse;
import grepp.NBE5_6_2_Team03.domain.mail.service.MailServiceClient;
import grepp.NBE5_6_2_Team03.domain.travelplan.service.TravelPlanQueryService;
import grepp.NBE5_6_2_Team03.domain.user.CustomUserDetails;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {

    private final MailServiceClient mailServiceClient;
    private final TravelPlanQueryService travelPlanQueryService;
    private final ObjectMapper objectMapper;

    @PostMapping("/send")
    public String sendSettlementMail(@RequestParam Long planId, @AuthenticationPrincipal CustomUserDetails customUser) {

        TravelPlanAdjustResponse response = travelPlanQueryService.getAdjustmentInfo(planId);

        Map<String, Object> templateModel = objectMapper.convertValue(response, new TypeReference<>() {});
        Map<String, Object> wrappedModel = Map.of("response", templateModel);

        mailServiceClient.sendHtml(
            customUser.getUser().getEmail(),
            "정산 결과 안내",
            "settlement-template",
            wrappedModel
        ).subscribe();
        return "redirect:/users/home";
    }
}
