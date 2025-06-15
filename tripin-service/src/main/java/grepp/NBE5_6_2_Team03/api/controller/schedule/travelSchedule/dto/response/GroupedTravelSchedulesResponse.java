package grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule.dto.response;

import grepp.NBE5_6_2_Team03.domain.travelschedule.TravelSchedule;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupedTravelSchedulesResponse {

    private Map<LocalDate, List<TravelScheduleResponse>> groupedTravelSchedules;

    public GroupedTravelSchedulesResponse(Map<LocalDate, List<TravelScheduleResponse>> groupedTravelSchedules) {
        this.groupedTravelSchedules = groupedTravelSchedules;
    }

    public static GroupedTravelSchedulesResponse from(List<TravelSchedule> travelSchedules) {
        Map<LocalDate, List<TravelScheduleResponse>> groupByDateScheduleMap = travelSchedules.stream()
                .collect(Collectors.groupingBy(
                        TravelSchedule::getTravelScheduleDate,
                        Collectors.mapping(
                                TravelScheduleResponse::fromEntity,
                                Collectors.toList()
                        )
                ));
        return new GroupedTravelSchedulesResponse(groupByDateScheduleMap);
    }

}
