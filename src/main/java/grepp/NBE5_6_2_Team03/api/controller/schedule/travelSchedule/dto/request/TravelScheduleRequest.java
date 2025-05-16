package grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule.dto.request;

import grepp.NBE5_6_2_Team03.domain.schedule.travelschedule.TravelSchedule;
import grepp.NBE5_6_2_Team03.domain.schedule.travelschedule.code.ScheduleStatus;
import grepp.NBE5_6_2_Team03.domain.travelplan.TravelPlan;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TravelScheduleRequest {

    @NotBlank
    private String content;
    private String placeName;
    private String departure;
    private String destination;
    private String transportation;
    @NotBlank
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate travelScheduleDate;

    public TravelSchedule toEntity(TravelPlan plan) {
        return TravelSchedule.builder()
            .travelPlan(plan)
            .content(this.content)
            .placeName(this.placeName)
            .departure(this.departure)
            .destination(this.destination)
            .transportation(this.transportation)
            .scheduleStatus(ScheduleStatus.PLANNED)
            .travelScheduleDate(this.travelScheduleDate)
            .createdDateTime(LocalDateTime.now())
            .build();
    }

    public static TravelScheduleRequest fromEntity(TravelSchedule schedule) {
        return new TravelScheduleRequest(
            schedule.getContent(),
            schedule.getPlaceName(),
            schedule.getDeparture(),
            schedule.getDestination(),
            schedule.getTransportation(),
            schedule.getTravelScheduleDate()
        );
    }
}
