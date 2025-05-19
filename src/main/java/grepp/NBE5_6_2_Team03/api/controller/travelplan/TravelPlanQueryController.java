package grepp.NBE5_6_2_Team03.api.controller.travelplan;

import grepp.NBE5_6_2_Team03.api.controller.travelplan.dto.response.TravelPlanAdjustResponse;
import grepp.NBE5_6_2_Team03.domain.exchange.service.ExchangeService;
import grepp.NBE5_6_2_Team03.domain.travelplan.service.TravelPlanQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/plan")
@RequiredArgsConstructor
public class TravelPlanQueryController {

    private final TravelPlanQueryService travelPlanQueryService;
    private final ExchangeService exchangeService;

    @GetMapping("/{travelPlanId}/expense")
    public String getAdjustmentInfo(@PathVariable Long travelPlanId, Model model) {
        TravelPlanAdjustResponse response = travelPlanQueryService.getAdjustmentInfo(travelPlanId);
        model.addAttribute("response", response);

        String curUnit = response.getCurUnit();
        int compareResult = exchangeService.compareLatestRateToAverageRate(curUnit);
        model.addAttribute("compareResult", compareResult);

        return "plan/expense";
    }
}
