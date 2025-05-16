package grepp.NBE5_6_2_Team03.api.controller.travelplan;

import grepp.NBE5_6_2_Team03.api.controller.travelplan.dto.TravelPlanRequestDto;
import grepp.NBE5_6_2_Team03.domain.plan.entity.TravelPlan;
import grepp.NBE5_6_2_Team03.domain.plan.service.TravelPlanService;
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

    @GetMapping
    public String listPlans(@AuthenticationPrincipal CustomUserDetails customUser, Model model) {
        List<TravelPlan> plans = travelPlanService.getPlansByUser(customUser.getId());
        model.addAttribute("plans", plans);
        model.addAttribute("user", customUser);
        return "plan/plan";
    }

    @PostMapping("/create")
    public String createPlan(@AuthenticationPrincipal CustomUserDetails customUser,@RequestBody TravelPlanRequestDto planDto) {
        travelPlanService.createPlan(customUser.getId(),planDto);
        return "redirect:/plan";
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public TravelPlanRequestDto getPlan(@PathVariable Long id) {
        return travelPlanService.getPlan(id);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updatePlan(@PathVariable Long id,
        @RequestBody TravelPlanRequestDto planDto) {
        try {
            travelPlanService.updatePlan(id, planDto);
            return ResponseEntity.ok("수정 완료");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping("/delete/{id}")
    public String deletePlan(@PathVariable Long id) {
        travelPlanService.deletePlan(id);
        return "redirect:/plan";
    }

}
