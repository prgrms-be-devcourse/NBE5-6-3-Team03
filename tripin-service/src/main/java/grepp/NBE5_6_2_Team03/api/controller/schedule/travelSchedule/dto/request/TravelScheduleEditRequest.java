package grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule.dto.request;

import grepp.NBE5_6_2_Team03.domain.travelplan.TravelPlan;
import grepp.NBE5_6_2_Team03.domain.travelschedule.ScheduleStatus;
import grepp.NBE5_6_2_Team03.domain.travelschedule.TravelRoute;
import grepp.NBE5_6_2_Team03.domain.travelschedule.TravelSchedule;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter @Setter
public class TravelScheduleEditRequest {

    private TravelRoute travelRoute;

    @NotBlank
    private String content;
    private String placeName;

    @NotBlank
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime travelScheduleDate;
    private int expense;

    public TravelScheduleEditRequest() {
    }

    @Builder
    private TravelScheduleEditRequest(TravelRoute travelRoute, String content, String placeName, LocalDateTime travelScheduleDate, int expense) {
        this.travelRoute = travelRoute;
        this.content = content;
        this.placeName = placeName;
        this.travelScheduleDate = travelScheduleDate;
        this.expense = expense;
    }

    public TravelSchedule toEntity(TravelPlan plan) {
        return TravelSchedule.builder()
            .travelPlan(plan)
            .travelRoute(travelRoute)
            .content(this.content)
            .placeName(this.placeName)
            .travelRoute(travelRoute)
            .scheduleStatus(ScheduleStatus.PLANNED)
            .travelScheduleDate(this.travelScheduleDate)
            .expense(this.expense)
            .build();
    }
}
