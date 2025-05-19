package grepp.NBE5_6_2_Team03.api.controller.travelplan;

import grepp.NBE5_6_2_Team03.api.controller.travelplan.dto.request.TravelPlanRequestDto;
import grepp.NBE5_6_2_Team03.domain.travelplan.TravelPlan;
import grepp.NBE5_6_2_Team03.domain.travelplan.service.TravelPlanService;
import grepp.NBE5_6_2_Team03.domain.user.CustomUserDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/plan")
@RequiredArgsConstructor
public class TravelPlanController {

    private final TravelPlanService travelPlanService;

    @PostMapping("/create")
    @ResponseBody
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
    @ResponseBody
    public TravelPlanRequestDto getPlan(@PathVariable("id") Long id) {
        return travelPlanService.getPlan(id);
    }

    @PatchMapping("/update/{id}")
    @ResponseBody
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
    public String deletePlan(@PathVariable Long id) {
        travelPlanService.deletePlan(id);
        return "redirect:/users/home";
    }

}
