package grepp.NBE5_6_2_Team03.api.controller.mail;

import grepp.NBE5_6_2_Team03.api.controller.adjustment.dto.response.AdjustmentResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import grepp.NBE5_6_2_Team03.domain.mail.service.MailServiceClient;
import grepp.NBE5_6_2_Team03.domain.adjustment.service.AdjustmentService;
import grepp.NBE5_6_2_Team03.domain.mail.service.MimeMailService;
import grepp.NBE5_6_2_Team03.domain.user.CustomUserDetails;
import grepp.NBE5_6_2_Team03.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {

    private final MailServiceClient mailServiceClient;
    private final MimeMailService mimeMailService;
    private final AdjustmentService travelPlanQueryService;
    private final ObjectMapper objectMapper;

    @GetMapping("/send/{plan-id}")
    public ApiResponse<Void> sendSettlementMail(@PathVariable("plan-id") Long planId,
        @AuthenticationPrincipal CustomUserDetails customUser) {

        AdjustmentResponse response = travelPlanQueryService.getAdjustmentInfo(planId);

//        Map<String, Object> templateModel = objectMapper.convertValue(response,
//            new TypeReference<>() {
//            });
//        Map<String, Object> wrappedModel = Map.of("response", response);

//        mailServiceClient.sendHtml(     // 메일 서버로 전송
//            customUser.getUser().getEmail(),
//            "정산 결과 안내",
//            "settlement-template",
//            wrappedModel
//        ).subscribe();

        mimeMailService.sendSettlementMail(
            customUser.getUser().getEmail(),
            "정산 결과 안내",
            "settlement-summary",
            response
        );
        return ApiResponse.noContent();
    }
}
