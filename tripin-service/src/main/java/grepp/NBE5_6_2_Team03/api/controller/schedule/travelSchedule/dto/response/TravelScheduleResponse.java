package grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule.dto.response;

import grepp.NBE5_6_2_Team03.domain.travelschedule.TravelSchedule;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TravelScheduleResponse {

    private final String username;
    private final Long travelScheduleId;
    private final Long travelPlanId;
    private final String content;
    private final String placeName;
    private final String status;
    private final LocalDateTime travelScheduleDate;
    private final String departure;
    private final String destination;
    private final String transportation;
    private final int price;

    @Builder
    public TravelScheduleResponse(String username, Long travelScheduleId, Long travelPlanId, String content, String placeName,
                                  String status, LocalDateTime travelDate, String departure, String destination, String transportation, int price) {
        this.username = username;
        this.travelScheduleId = travelScheduleId;
        this.travelPlanId = travelPlanId;
        this.content = content;
        this.placeName = placeName;
        this.status = status;
        this.travelScheduleDate = travelDate;
        this.departure = departure;
        this.destination = destination;
        this.transportation = transportation;
        this.price = price;
    }

    public static TravelScheduleResponse fromEntity(TravelSchedule schedule) {
        return fromEntity(null, schedule);
    }

    public static TravelScheduleResponse fromEntity(String username, TravelSchedule schedule) {
        return TravelScheduleResponse.builder()
            .username(username)
            .travelScheduleId(schedule.getTravelScheduleId())
            .travelPlanId(schedule.getTravelPlan().getTravelPlanId())
            .content(schedule.getContent())
            .placeName(schedule.getPlaceName())
            .status(schedule.getScheduleStatus().name())
            .travelDate(schedule.getTravelScheduleDate())
            .departure(schedule.getTravelRoute().getDeparture())
            .destination(schedule.getTravelRoute().getDestination())
            .transportation(schedule.getTravelRoute().getTransportation())
            .price(schedule.getPrice())
            .build();
    }
}
