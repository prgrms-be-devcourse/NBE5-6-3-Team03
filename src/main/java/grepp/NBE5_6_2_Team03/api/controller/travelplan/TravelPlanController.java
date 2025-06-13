package grepp.NBE5_6_2_Team03.api.controller.travelplan;

import grepp.NBE5_6_2_Team03.api.controller.travelplan.dto.request.TravelPlanRequestDto;
import grepp.NBE5_6_2_Team03.domain.travelplan.service.TravelPlanService;
import grepp.NBE5_6_2_Team03.domain.user.CustomUserDetails;
import grepp.NBE5_6_2_Team03.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plan")
@RequiredArgsConstructor
public class TravelPlanController {

    private final TravelPlanService travelPlanService;

    @PostMapping("/create")
    public ResponseEntity<String> createPlan(@AuthenticationPrincipal CustomUserDetails customUser,
        @RequestBody TravelPlanRequestDto planDto) {
        try {
            travelPlanService.createPlan(customUser.getId(), planDto);
            return ResponseEntity.ok("success");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/api/{id}")
    public TravelPlanRequestDto getPlan(@PathVariable("id") Long id) {
        return travelPlanService.getPlan(id);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updatePlan(@PathVariable("id") Long id,
        @RequestBody TravelPlanRequestDto planDto) {
        try {
            travelPlanService.updatePlan(id, planDto);
            return ResponseEntity.ok("수정 완료");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/delete/{id}")
    public ApiResponse<Void> deletePlan(@PathVariable Long id) {
        travelPlanService.deletePlan(id);
        return ApiResponse.noContent();
    }

}
