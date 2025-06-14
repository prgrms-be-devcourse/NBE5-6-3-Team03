package grepp.NBE5_6_2_Team03.domain.travelplan.service;

import grepp.NBE5_6_2_Team03.api.controller.travelplan.dto.response.TravelPlanAdjustResponse;
import grepp.NBE5_6_2_Team03.api.controller.travelplan.dto.response.TravelScheduleExpenseInfo;
import grepp.NBE5_6_2_Team03.domain.exchange.service.ExchangeService;
import grepp.NBE5_6_2_Team03.domain.expense.Expense;
import grepp.NBE5_6_2_Team03.domain.travelplan.TravelPlan;
import grepp.NBE5_6_2_Team03.domain.travelplan.repository.TravelPlanQueryRepository;
import grepp.NBE5_6_2_Team03.domain.travelschedule.TravelSchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TravelPlanQueryService {

    private final TravelPlanQueryRepository planQueryRepository;
    private final ExchangeService exchangeService;

    public TravelPlanAdjustResponse getAdjustmentInfo(Long travelPlanId){
        TravelPlan travelPlan = planQueryRepository.getTravelPlanFetchScheduleAndExpense(travelPlanId);
        List<TravelSchedule> travelSchedules = travelPlan.getTravelSchedules();
        List<Expense> expenses = findNotNullExpense(travelSchedules);

        String curUnit = travelPlan.getCountryStatus().getCode();

        int remainMoney = getRemainMoney(travelPlan.getPublicMoney(), expenses);
        int personalPrice = getPersonalPrice(remainMoney, travelPlan.getCount());
        int rateCompareResult = exchangeService.compareLatestRateToAverageRate(curUnit);

        int latestExchangeRate = exchangeService.getLatestExchangeRateInt(curUnit);
        int exchangePersonalPrice = exchangeService.exchangeToWon(curUnit, personalPrice);

        List<TravelScheduleExpenseInfo> expenseInfos = TravelScheduleExpenseInfo.convertBy(travelSchedules);
        return TravelPlanAdjustResponse.of(expenseInfos, travelPlan, getTotalExpenses(expenses), remainMoney, personalPrice, latestExchangeRate, exchangePersonalPrice, curUnit, rateCompareResult);
    }

    private List<Expense> findNotNullExpense(List<TravelSchedule> travelSchedules) {
        return travelSchedules.stream()
                .map(TravelSchedule::getExpense)
                .filter(Objects::nonNull)
                .toList();
    }

    private int getRemainMoney(int publicMoney, List<Expense> expenses) {
        return publicMoney - getTotalExpenses(expenses);
    }

    private int getTotalExpenses(List<Expense> expenses) {
        return expenses.stream()
            .mapToInt(Expense::getPayedPrice)
                .sum();
    }

    private int getPersonalPrice(int remainMoney, int count) {
        return remainMoney / count;
    }
}
