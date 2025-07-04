package grepp.NBE5_6_2_Team03.api.controller.adjustment.dto.response;

import grepp.NBE5_6_2_Team03.domain.travelschedule.TravelRoute;
import grepp.NBE5_6_2_Team03.domain.travelschedule.TravelSchedule;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class AdjustmentExpenseInfo {

    private String placeName;
    private String content;
    private TravelRoute travelRoute;
    private int expense;

    @Builder
    private AdjustmentExpenseInfo(String placeName, String content, TravelRoute travelRoute, int expense) {
        this.placeName = placeName;
        this.content = content;
        this.travelRoute = travelRoute;
        this.expense = expense;
    }

    public static List<AdjustmentExpenseInfo> convertBy(List<TravelSchedule> travelSchedules) {
       return travelSchedules.stream()
                .map(travelSchedule -> AdjustmentExpenseInfo.of(
                        travelSchedule.getTravelRoute(),
                        travelSchedule.getContent(),
                        travelSchedule.getPlaceName(),
                        travelSchedule.getExpense()
                ))
               .toList();
    }

    public static AdjustmentExpenseInfo of(TravelRoute travelRoute, String content, String placeName, int expense){
        return AdjustmentExpenseInfo.builder()
                .travelRoute(travelRoute)
                .content(content)
                .placeName(placeName)
                .expense(expense)
                .build();
    }
}
