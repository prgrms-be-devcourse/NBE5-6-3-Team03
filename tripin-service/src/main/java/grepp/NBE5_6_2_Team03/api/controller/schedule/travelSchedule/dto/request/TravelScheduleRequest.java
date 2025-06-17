package grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule.dto.request;

import grepp.NBE5_6_2_Team03.api.controller.schedule.traveltimeai.dto.TravelTimeRequest;
import grepp.NBE5_6_2_Team03.api.controller.schedule.traveltimeai.dto.TravelTimeResponse;
import grepp.NBE5_6_2_Team03.domain.schedule.treveltimeai.service.TravelTimeAiService;
import grepp.NBE5_6_2_Team03.domain.travelschedule.TravelRoute;
import grepp.NBE5_6_2_Team03.domain.travelschedule.TravelSchedule;
import grepp.NBE5_6_2_Team03.domain.travelschedule.ScheduleStatus;
import grepp.NBE5_6_2_Team03.domain.travelplan.TravelPlan;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter @Setter
public class TravelScheduleRequest {

    private TravelRoute travelRoute;

    @NotBlank
    private String content;
    private String placeName;

    @NotBlank
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime travelScheduleDate;
    private int expense;

    public TravelScheduleRequest() {
    }

    @Builder
    private TravelScheduleRequest(TravelRoute travelRoute, String content, String placeName, LocalDateTime travelScheduleDate, int expense) {
        this.travelRoute = travelRoute;
        this.content = content;
        this.placeName = placeName;
        this.travelScheduleDate = travelScheduleDate;
        this.expense = expense;
    }

    public TravelSchedule toEntity(TravelPlan plan,TravelScheduleRequest request, TravelTimeAiService aiService) {

        if (travelRouteExist(request)) {

            TravelTimeRequest aiRequest = new TravelTimeRequest(
                this.travelRoute.getDeparture(),
                this.travelRoute.getDestination(),
                this.travelRoute.getTransportation()
            );

            TravelTimeResponse aiResponse = aiService.predictTime(aiRequest);

            travelRoute = new TravelRoute(
                aiRequest.getDeparture(),
                aiRequest.getDestination(),
                aiRequest.getTransport(),
                aiResponse.getExpectedTime()
            );
        }
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

    private Boolean travelRouteExist(TravelScheduleRequest travelRoute) {
        return travelRoute != null;
    }
}
