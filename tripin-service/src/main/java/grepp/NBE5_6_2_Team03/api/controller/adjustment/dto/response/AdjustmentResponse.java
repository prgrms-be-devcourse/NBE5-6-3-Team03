package grepp.NBE5_6_2_Team03.api.controller.adjustment.dto.response;

import grepp.NBE5_6_2_Team03.domain.exchange.type.ExchangeRateComparison;
import grepp.NBE5_6_2_Team03.domain.travelplan.TravelPlan;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class AdjustmentResponse {

    private final List<AdjustmentExpenseInfo> expenses;
    private final String country;
    private final String curUnit;
    private final int publicMoney;
    private final int applicants;
    private final int totalExpense;
    private final int lastestExchangeRate;
    private final ExchangeRateComparison compareLatestRateToAverageRate;
    private final AdjustmentAmount adjustmentAmount;
    private final PersonalAdjustmentAmount personalAdjustmentAmount;

    @Builder
    private AdjustmentResponse(List<AdjustmentExpenseInfo> expenses, String country, String curUnit, int publicMoney,
                               int applicants, int totalExpense, int lastestExchangeRate, ExchangeRateComparison compareLatestRateToAverageRate,
                               AdjustmentAmount adjustmentAmount, PersonalAdjustmentAmount personalAdjustmentAmount) {
        this.expenses = expenses;
        this.country = country;
        this.curUnit = curUnit;
        this.publicMoney = publicMoney;
        this.applicants = applicants;
        this.totalExpense = totalExpense;
        this.lastestExchangeRate = lastestExchangeRate;
        this.compareLatestRateToAverageRate = compareLatestRateToAverageRate;
        this.adjustmentAmount = adjustmentAmount;
        this.personalAdjustmentAmount = personalAdjustmentAmount;
    }

    public static AdjustmentResponse of(List<AdjustmentExpenseInfo> expenseInfos, TravelPlan travelPlan, int totalExpense,
                                        int lastestExchangeRate, ExchangeRateComparison compareLatestRateToAverageRate,
                                        AdjustmentAmount adjustmentAmount, PersonalAdjustmentAmount personalAdjustmentAmount) {
        return AdjustmentResponse.builder()
            .expenses(expenseInfos)
            .country(travelPlan.getCountry().getCountryName())
            .curUnit(travelPlan.getCurrentUnit().getUnit())
            .publicMoney(travelPlan.getPublicMoney())
            .applicants(travelPlan.getApplicants())
            .totalExpense(totalExpense)
            .lastestExchangeRate(lastestExchangeRate)
            .compareLatestRateToAverageRate(compareLatestRateToAverageRate)
            .adjustmentAmount(adjustmentAmount)
            .personalAdjustmentAmount(personalAdjustmentAmount)
            .build();
    }
}
