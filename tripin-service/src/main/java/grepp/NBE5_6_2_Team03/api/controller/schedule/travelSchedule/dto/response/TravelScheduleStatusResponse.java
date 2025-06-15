package grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule.dto.response;

import grepp.NBE5_6_2_Team03.domain.travelschedule.ScheduleStatus;
import grepp.NBE5_6_2_Team03.domain.travelschedule.TravelSchedule;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TravelScheduleStatusResponse {

    private final Long travelScheduleId;
    private final ScheduleStatus status;

    @Builder
    public TravelScheduleStatusResponse(Long travelScheduleId, ScheduleStatus status) {
        this.travelScheduleId = travelScheduleId;
        this.status = status;
    }

    public static TravelScheduleStatusResponse fromEntity(TravelSchedule schedule) {
        return TravelScheduleStatusResponse.builder()
            .travelScheduleId(schedule.getTravelScheduleId())
            .status(schedule.getScheduleStatus())
            .build();
    }
}
