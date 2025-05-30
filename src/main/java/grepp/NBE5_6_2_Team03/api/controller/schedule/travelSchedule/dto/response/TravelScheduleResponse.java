package grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule.dto.response;

import grepp.NBE5_6_2_Team03.domain.travelplan.TravelPlan;
import grepp.NBE5_6_2_Team03.domain.travelschedule.ScheduleStatus;
import grepp.NBE5_6_2_Team03.domain.travelschedule.TravelRoute;
import grepp.NBE5_6_2_Team03.domain.travelschedule.TravelSchedule;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
public class TravelScheduleResponse {

    private final Long travelScheduleId;
    private final Long travelPlanId;
    private final String content;
    private final String placeName;
    private final String status;
    private final LocalDate travelDate;
    private final String departure;
    private final String destination;
    private final String transportation;

    @Builder
    public TravelScheduleResponse(Long travelScheduleId, Long travelPlanId, String content, String placeName, String status,
                                  LocalDate travelDate, String departure, String destination, String transportation) {
        this.travelScheduleId = travelScheduleId;
        this.travelPlanId = travelPlanId;
        this.content = content;
        this.placeName = placeName;
        this.status = status;
        this.travelDate = travelDate;
        this.departure = departure;
        this.destination = destination;
        this.transportation = transportation;
    }

    public static TravelScheduleResponse fromEntity(TravelSchedule schedule) {
        return TravelScheduleResponse.builder()
            .travelScheduleId(schedule.getTravelScheduleId())
            .travelPlanId(schedule.getTravelPlan().getTravelPlanId())
            .content(schedule.getContent())
            .placeName(schedule.getPlaceName())
            .status(schedule.getScheduleStatus().name())
            .travelDate(schedule.getTravelScheduleDate())
            .departure(schedule.getTravelRoute().getDeparture())
            .destination(schedule.getTravelRoute().getDestination())
            .transportation(schedule.getTravelRoute().getTransportation())
            .build();
    }
}
