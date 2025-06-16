package grepp.NBE5_6_2_Team03.domain.adjustment.service;

import grepp.NBE5_6_2_Team03.api.controller.adjustment.dto.response.AdjustmentAmount;
import grepp.NBE5_6_2_Team03.api.controller.adjustment.dto.response.AdjustmentResponse;
import grepp.NBE5_6_2_Team03.api.controller.adjustment.dto.response.AdjustmentExpenseInfo;
import grepp.NBE5_6_2_Team03.api.controller.adjustment.dto.response.PersonalAdjustmentAmount;
import grepp.NBE5_6_2_Team03.domain.exchange.service.ExchangeService;
import grepp.NBE5_6_2_Team03.domain.exchange.type.ExchangeRateComparison;
import grepp.NBE5_6_2_Team03.domain.travelplan.TravelPlan;
import grepp.NBE5_6_2_Team03.domain.adjustment.respository.AdjustmentRepository;
import grepp.NBE5_6_2_Team03.domain.travelschedule.ScheduleStatus;
import grepp.NBE5_6_2_Team03.domain.travelschedule.TravelSchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AdjustmentService {

    private final AdjustmentRepository planQueryRepository;
    private final ExchangeService exchangeService;

    public AdjustmentResponse getAdjustmentInfo(Long travelPlanId){
        TravelPlan travelPlan = planQueryRepository.getTravelPlanWithSchedules(travelPlanId);
        List<TravelSchedule> completedSchedules = travelPlan.getTravelSchedules().stream()
            .filter(s -> s.getScheduleStatus() == ScheduleStatus.COMPLETED)
            .collect(Collectors.toList());

        String curUnit = travelPlan.getCurrentUnit().getUnit();
        boolean isKRW = travelPlan.getCurrentUnit().isKRW();
        int totalExpense = getTotalExpense(completedSchedules);
        int applicants = travelPlan.getApplicants();

        int remainMoney = getRemainMoney(travelPlan.getPublicMoney(), completedSchedules);
        int lastestExchangeRate = exchangeService.getLatestExchangeRateInt(curUnit);

        ExchangeRateComparison compareLatestRateToAverageRate = exchangeService.compareLatestRateToAverageRate(curUnit);

        AdjustmentAmount adjustmentAmount = calculateAdjustmentAmount(remainMoney, isKRW, curUnit);

        int personalAmount = Math.abs(remainMoney) / applicants;
        PersonalAdjustmentAmount personalAdjustmentAmount = calculatePersonalAdjustmentAmount(remainMoney, personalAmount, isKRW, curUnit);

        List<AdjustmentExpenseInfo> expenses = AdjustmentExpenseInfo.convertBy(completedSchedules);

        return AdjustmentResponse.of(expenses, travelPlan, totalExpense, lastestExchangeRate,
                                     compareLatestRateToAverageRate, adjustmentAmount, personalAdjustmentAmount);
    }

    private int getRemainMoney(int publicMoney, List<TravelSchedule> schedules) {
        return publicMoney - getTotalExpense(schedules);
    }

    private int getTotalExpense(List<TravelSchedule> schedules) {
        return schedules.stream()
            .mapToInt(TravelSchedule::getExpense)
            .sum();
    }

    private AdjustmentAmount calculateAdjustmentAmount(int remainMoney, boolean isKRW, String curUnit) {
        int won = isKRW ? Math.abs(remainMoney) : exchangeService.exchangeToWon(curUnit, Math.abs(remainMoney));
        int foreign = isKRW ? exchangeService.exchangeToForeign(curUnit, Math.abs(remainMoney)) : Math.abs(remainMoney);

        return AdjustmentAmount.builder()
            .wonToPay(remainMoney < 0 ? won : 0)
            .foreignToPay(remainMoney < 0 ? foreign : 0)
            .wonToReceive(remainMoney > 0 ? won : 0)
            .foreignToReceive(remainMoney > 0 ? foreign : 0)
            .build();
    }

    private PersonalAdjustmentAmount calculatePersonalAdjustmentAmount(int remainMoney, int personalAmount, boolean isKRW, String curUnit) {
        int won = isKRW ? personalAmount : exchangeService.exchangeToWon(curUnit, personalAmount);
        int foreign = isKRW ? exchangeService.exchangeToForeign(curUnit, personalAmount) : personalAmount;

        return PersonalAdjustmentAmount.builder()
            .personalWonToPay(remainMoney < 0 ? won : 0)
            .personalForeignToPay(remainMoney < 0 ? foreign : 0)
            .personalWonToReceive(remainMoney > 0 ? won : 0)
            .personalForeignToReceive(remainMoney > 0 ? foreign : 0)
            .build();
    }
}
