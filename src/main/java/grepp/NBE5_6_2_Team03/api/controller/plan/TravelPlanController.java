package grepp.NBE5_6_2_Team03.api.controller.plan;

import grepp.NBE5_6_2_Team03.api.controller.plan.dto.TravelPlanDto;
import grepp.NBE5_6_2_Team03.domain.plan.service.TravelPlanService;
import grepp.NBE5_6_2_Team03.domain.plan.entity.TravelPlan;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/plan")
@RequiredArgsConstructor
public class TravelPlanController {

    private final TravelPlanService travelPlanService;


    @GetMapping
    public String listPlans(Model model) {
        List<TravelPlan> plans = travelPlanService.getPlansByUser();
        model.addAttribute("plans", plans);
        return "plan/plan";
    }


    @PostMapping("/create")
    public String createPlan(@RequestBody TravelPlanDto planDto) {
        travelPlanService.createPlan(planDto);
        return "redirect:/plan";
    }
}
