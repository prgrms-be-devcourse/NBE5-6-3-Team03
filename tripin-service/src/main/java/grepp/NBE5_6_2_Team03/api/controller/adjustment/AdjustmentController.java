package grepp.NBE5_6_2_Team03.api.controller.adjustment;

import grepp.NBE5_6_2_Team03.api.controller.adjustment.dto.response.AdjustmentResponse;
import grepp.NBE5_6_2_Team03.domain.adjustment.service.AdjustmentService;
import grepp.NBE5_6_2_Team03.domain.user.CustomUserDetails;
import grepp.NBE5_6_2_Team03.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/travel-plans")
@RequiredArgsConstructor
@RestController
public class AdjustmentController {

    private final AdjustmentService travelPlanQueryService;

    @GetMapping("/{travelPlanId}/adjustment")
    public ApiResponse<Map<String, Object>> getAdjustmentInfo(@PathVariable("travelPlanId") Long travelPlanId, @AuthenticationPrincipal CustomUserDetails customUser) {
        AdjustmentResponse adjustmentResponse = travelPlanQueryService.getAdjustmentInfo(travelPlanId);

        Map<String, Object> response = new HashMap<>();
        response.put("response", adjustmentResponse);
        response.put("username", customUser.getUsername());
        response.put("userEmail", customUser.getUser().getEmail());

        return ApiResponse.success(response);
    }
}
