package grepp.NBE5_6_2_Team03.api.controller.travelplan;

import grepp.NBE5_6_2_Team03.api.controller.travelplan.dto.request.TravelPlanEditRequest;
import grepp.NBE5_6_2_Team03.api.controller.travelplan.dto.request.TravelPlanSaveRequest;
import grepp.NBE5_6_2_Team03.api.controller.travelplan.dto.response.TravelPlanInfo;
import grepp.NBE5_6_2_Team03.api.controller.travelplan.dto.response.TravelPlansResponse;

import grepp.NBE5_6_2_Team03.domain.travelplan.service.TravelPlanService;
import grepp.NBE5_6_2_Team03.domain.user.CustomUserDetails;
import grepp.NBE5_6_2_Team03.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/travel-plans")
@RequiredArgsConstructor
public class TravelPlanController {

    private final TravelPlanService travelPlanService;

    @GetMapping("/{id}")
    public ApiResponse<TravelPlanInfo> getTravelPlan(@PathVariable("id") Long travelPlanId) {
        return ApiResponse.success(travelPlanService.getMyPlan(travelPlanId));
    }

    @GetMapping
    public ApiResponse<TravelPlansResponse> getTravelPlans(@AuthenticationPrincipal CustomUserDetails user) {
        return ApiResponse.success(travelPlanService.getMyPlans(user.getId(), user.getUsername()));
    }

    @PostMapping("/new")
    public ApiResponse<Long> create(@RequestBody TravelPlanSaveRequest request,
                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ApiResponse.success(travelPlanService.create(request, userDetails.getId()));
    }

    @PatchMapping("/{id}")
    public ApiResponse<Long> modifyPlan(@RequestBody TravelPlanEditRequest request,
                                        @PathVariable("id") Long id) {
        return ApiResponse.success(travelPlanService.modifyPlan(request, id));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePlan(@PathVariable("id") Long id) {
        travelPlanService.deletePlan(id);
        return ApiResponse.noContent();
    }

}
