package grepp.NBE5_6_2_Team03.domain.travelplan.service;

import grepp.NBE5_6_2_Team03.api.controller.travelplan.dto.response.TravelPlanAdjustResponse;
import grepp.NBE5_6_2_Team03.api.controller.travelplan.dto.response.TravelScheduleExpenseInfo;
import grepp.NBE5_6_2_Team03.domain.exchange.service.ExchangeService;
import grepp.NBE5_6_2_Team03.domain.travelplan.TravelPlan;
import grepp.NBE5_6_2_Team03.domain.travelplan.repository.TravelPlanQueryRepository;
import grepp.NBE5_6_2_Team03.domain.travelschedule.TravelSchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TravelPlanQueryService {

    private final TravelPlanQueryRepository planQueryRepository;
    private final ExchangeService exchangeService;

    public TravelPlanAdjustResponse getAdjustmentInfo(Long travelPlanId){
        TravelPlan travelPlan = planQueryRepository.getTravelPlanFetchSchedule(travelPlanId);
        List<TravelSchedule> travelSchedules = travelPlan.getTravelSchedules();

        String curUnit = travelPlan.getCountry().getCode();
        int remainMoney = getRemainMoney(travelPlan.getPublicMoney(), travelSchedules);
        int personalPrice = getPersonalPrice(remainMoney, travelPlan.getApplicants());
        int rateCompareResult = exchangeService.compareLatestRateToAverageRate(curUnit);

        int latestExchangeRate = exchangeService.getLatestExchangeRateInt(curUnit);
        int exchangePersonalPrice = exchangeService.exchangeToWon(curUnit, personalPrice);

        List<TravelScheduleExpenseInfo> expenseInfos = TravelScheduleExpenseInfo.convertBy(travelSchedules);
        return TravelPlanAdjustResponse.of(expenseInfos, travelPlan, getTotalExpenses(travelSchedules), remainMoney, personalPrice, latestExchangeRate, exchangePersonalPrice, curUnit, rateCompareResult);
    }

    private void validatePrice(TravelPlan plan, int newPayedPrice) {
        if (getTotalPrice(plan) + newPayedPrice > plan.getPublicMoney()) {
            throw new IllegalArgumentException("공금이 부족합니다.");
        }
    }

    private void validatePriceForEdit(TravelPlan plan, int oldPayedPrice, int newPayedPrice) {
        int totalSum = getTotalPrice(plan) - oldPayedPrice + newPayedPrice;
        if (totalSum > plan.getPublicMoney()) {
            throw new IllegalArgumentException("공금이 부족합니다.");
        }
    }

    private int getRemainMoney(int publicMoney, List<TravelSchedule> schedules) {
        return publicMoney - getTotalExpenses(schedules);
    }

    private int getTotalExpenses(List<TravelSchedule> schedules) {
        return schedules.stream()
            .map(TravelSchedule::getExpense)
            .mapToInt(Integer::intValue)
            .sum();
    }

    private int getTotalPrice(TravelPlan plan) {
        return getTotalExpenses(plan.getTravelSchedules());
    }

    private int getPersonalPrice(int remainMoney, int count) {
        return remainMoney / count;
    }
}
