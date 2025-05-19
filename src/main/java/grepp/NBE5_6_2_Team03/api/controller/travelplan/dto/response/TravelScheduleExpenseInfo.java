package grepp.NBE5_6_2_Team03.api.controller.travelplan.dto.response;

import grepp.NBE5_6_2_Team03.domain.travelschedule.TravelRoute;
import grepp.NBE5_6_2_Team03.domain.travelschedule.TravelSchedule;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class TravelScheduleExpenseInfo {

    private String placeName;
    private String content;
    private TravelRoute travelRoute;
    private int payedPrice;

    @Builder
    private TravelScheduleExpenseInfo(String placeName, String content, TravelRoute travelRoute, int payedPrice) {
        this.placeName = placeName;
        this.content = content;
        this.travelRoute = travelRoute;
        this.payedPrice = payedPrice;
    }

    public static List<TravelScheduleExpenseInfo> convertBy(List<TravelSchedule> travelSchedules) {
       return travelSchedules.stream()
                .filter(travelSchedule -> travelSchedule.getExpense() != null)
                .map(travelSchedule -> TravelScheduleExpenseInfo.of(
                        travelSchedule.getTravelRoute(),
                        travelSchedule.getContent(),
                        travelSchedule.getPlaceName(),
                        travelSchedule.getExpense().getPayedPrice()
                ))
               .toList();
    }

    public static TravelScheduleExpenseInfo of(TravelRoute travelRoute, String content, String placeName, int payedPrice){
        return TravelScheduleExpenseInfo.builder()
                .travelRoute(travelRoute)
                .content(content)
                .placeName(placeName)
                .payedPrice(payedPrice)
                .build();
    }
}
