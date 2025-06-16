package grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule.dto.response;

import grepp.NBE5_6_2_Team03.domain.travelschedule.TravelRoute;
import grepp.NBE5_6_2_Team03.domain.travelschedule.TravelSchedule;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TravelScheduleResponse {

    private final Long travelScheduleId;
    private final Long travelPlanId;
    private final String content;
    private final String placeName;
    private final String status;
    private final LocalDateTime travelScheduleDate;
    private final TravelRoute travelRoute;
    private final int expense;
    private final String currentUnit;

    @Builder
    public TravelScheduleResponse(Long travelScheduleId, Long travelPlanId, String content, String placeName, String status,
                                  LocalDateTime travelDate, TravelRoute travelRoute, int expense, String currentUnit) {
        this.travelScheduleId = travelScheduleId;
        this.travelPlanId = travelPlanId;
        this.content = content;
        this.placeName = placeName;
        this.status = status;
        this.travelScheduleDate = travelDate;
        this.travelRoute = travelRoute;
        this.expense = expense;
        this.currentUnit = currentUnit;
    }

    public static TravelScheduleResponse fromEntity(TravelSchedule schedule) {
        return TravelScheduleResponse.builder()
            .travelScheduleId(schedule.getTravelScheduleId())
            .travelPlanId(schedule.getTravelPlan().getId())
            .content(schedule.getContent())
            .placeName(schedule.getPlaceName())
            .status(schedule.getScheduleStatus().name())
            .travelDate(schedule.getTravelScheduleDate())
            .travelRoute(schedule.getTravelRoute())
            .expense(schedule.getExpense())
            .currentUnit(schedule.getTravelPlan().getCurrentUnit().getUnit())
            .build();
    }
}
