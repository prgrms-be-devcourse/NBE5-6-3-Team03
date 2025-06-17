package grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule.dto.request;

import grepp.NBE5_6_2_Team03.domain.schedule.treveltimeai.service.TravelTimeAiService;
import grepp.NBE5_6_2_Team03.domain.travelplan.TravelPlan;
import grepp.NBE5_6_2_Team03.domain.travelschedule.ScheduleStatus;
import grepp.NBE5_6_2_Team03.domain.travelschedule.TravelSchedule;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter @Setter
public class TravelScheduleEditRequest {

    private TravelRouteRequest travelRouteRequest = new TravelRouteRequest();

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
    private TravelScheduleEditRequest(TravelRouteRequest travelRouteRequest, String content, String placeName, LocalDateTime travelScheduleDate, int expense) {
        this.travelRouteRequest = (travelRouteRequest != null) ? travelRouteRequest : new TravelRouteRequest();
        this.content = content;
        this.placeName = placeName;
        this.travelScheduleDate = travelScheduleDate;
        this.expense = expense;
    }

    public TravelSchedule toEntity(TravelPlan plan, TravelTimeAiService aiService) {
        return TravelSchedule.builder()
            .travelPlan(plan)
            .content(this.content)
            .placeName(this.placeName)
            .travelRoute(this.travelRouteRequest.toEntity(travelRouteRequest, aiService))
            .scheduleStatus(ScheduleStatus.PLANNED)
            .travelScheduleDate(this.travelScheduleDate)
            .expense(this.expense)
            .build();
    }

}
