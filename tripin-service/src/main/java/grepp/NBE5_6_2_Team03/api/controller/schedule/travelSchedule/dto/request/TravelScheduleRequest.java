package grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule.dto.request;

import grepp.NBE5_6_2_Team03.domain.travelschedule.TravelRoute;
import grepp.NBE5_6_2_Team03.domain.travelschedule.TravelSchedule;
import grepp.NBE5_6_2_Team03.domain.travelschedule.ScheduleStatus;
import grepp.NBE5_6_2_Team03.domain.travelplan.TravelPlan;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter @Setter
public class TravelScheduleRequest {

    private String departure;
    private String destination;
    private String transportation;

    @NotBlank
    private String content;
    private String placeName;

    @NotBlank
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate travelScheduleDate;

    public TravelScheduleRequest() {
    }

    @Builder
    private TravelScheduleRequest(String departure, String destination, String transportation, String content, String placeName, LocalDate travelScheduleDate) {
        this.departure = departure;
        this.destination = destination;
        this.transportation = transportation;
        this.content = content;
        this.placeName = placeName;
        this.travelScheduleDate = travelScheduleDate;
    }

    public TravelSchedule toEntity(TravelPlan plan) {
        TravelRoute travelRoute = new TravelRoute(this.departure, this.destination, this.transportation);

        return TravelSchedule.builder()
            .travelPlan(plan)
            .content(this.content)
            .placeName(this.placeName)
            .travelRoute(travelRoute)
            .scheduleStatus(ScheduleStatus.PLANNED)
            .travelScheduleDate(this.travelScheduleDate)
            .build();
    }

    public static TravelScheduleRequest fromEntity(TravelSchedule schedule) {
        return TravelScheduleRequest.builder()
            .departure(schedule.getTravelRoute().getDeparture())
            .destination(schedule.getTravelRoute().getDestination())
            .transportation(schedule.getTravelRoute().getTransportation())
            .content(schedule.getContent())
            .placeName(schedule.getPlaceName())
            .travelScheduleDate(schedule.getTravelScheduleDate())
            .build();
    }
}
