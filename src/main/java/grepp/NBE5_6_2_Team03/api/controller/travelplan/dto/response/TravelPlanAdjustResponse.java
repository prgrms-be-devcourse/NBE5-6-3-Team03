package grepp.NBE5_6_2_Team03.api.controller.travelplan.dto.response;

import grepp.NBE5_6_2_Team03.domain.travelplan.TravelPlan;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class TravelPlanAdjustResponse {

    private List<TravelScheduleExpenseInfo> expenses;
    private String country;
    private int publicMoney;
    private int count;
    private int totalPrice;
    private int remainMoney;
    private int personalPrice;

    @Builder
    private TravelPlanAdjustResponse(List<TravelScheduleExpenseInfo> expenses, String country, int publicMoney,
                                    int count, int totalPrice, int personalPrice, int remainMoney) {
        this.expenses = expenses;
        this.country = country;
        this.publicMoney = publicMoney;
        this.count = count;
        this.totalPrice = totalPrice;
        this.personalPrice = personalPrice;
        this.remainMoney = remainMoney;
    }

    public static TravelPlanAdjustResponse of(List<TravelScheduleExpenseInfo> expenseInfos, TravelPlan travelPlan,
                                              int totalPrice, int remainMoney, int personalPrice) {
        return TravelPlanAdjustResponse.builder()
                .expenses(expenseInfos)
                .country(travelPlan.getCountry())
                .publicMoney(travelPlan.getPublicMoney())
                .count(travelPlan.getCount())
                .totalPrice(totalPrice)
                .personalPrice(personalPrice)
                .remainMoney(remainMoney)
                .build();
    }
}
