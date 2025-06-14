package grepp.NBE5_6_2_Team03.api.controller.travelplan;

import grepp.NBE5_6_2_Team03.api.controller.travelplan.dto.response.TravelPlanAdjustResponse;
import grepp.NBE5_6_2_Team03.domain.travelplan.service.TravelPlanQueryService;
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

@RestController
@RequestMapping("/plan")
@RequiredArgsConstructor
public class TravelPlanQueryController {

    private final TravelPlanQueryService travelPlanQueryService;

    @GetMapping("/{travelPlanId}/expense")
    public ApiResponse<Map<String, Object>> getAdjustmentInfo(@PathVariable("travelPlanId") Long travelPlanId, @AuthenticationPrincipal CustomUserDetails customUser) {
        TravelPlanAdjustResponse travelPlanAdjustResponse = travelPlanQueryService.getAdjustmentInfo(travelPlanId);

        Map<String, Object> response = new HashMap<>();
        response.put("response", travelPlanAdjustResponse);
        response.put("username", customUser.getUsername());
        response.put("userEmail", customUser.getUser().getEmail());

        return ApiResponse.success(response);
    }
}
