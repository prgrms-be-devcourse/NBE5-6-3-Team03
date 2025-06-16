package grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule.dto.request;

import grepp.NBE5_6_2_Team03.domain.travelschedule.ScheduleStatus;
import lombok.Getter;

@Getter
public class TravelScheduleStatusRequest {

    private ScheduleStatus status;

    public TravelScheduleStatusRequest() {
    }
}
