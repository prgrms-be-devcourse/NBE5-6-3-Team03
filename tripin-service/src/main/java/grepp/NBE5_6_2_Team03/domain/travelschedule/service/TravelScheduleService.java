package grepp.NBE5_6_2_Team03.domain.travelschedule.service;

import grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule.dto.request.TravelRouteRequest;
import grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule.dto.request.TravelScheduleEditRequest;
import grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule.dto.request.TravelScheduleRequest;
import grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule.dto.request.TravelScheduleStatusRequest;
import grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule.dto.response.GroupedTravelSchedulesResponse;
import grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule.dto.response.TravelScheduleEditResponse;
import grepp.NBE5_6_2_Team03.domain.schedule.treveltimeai.service.TravelTimeAiService;
import grepp.NBE5_6_2_Team03.domain.travelplan.TravelPlan;
import grepp.NBE5_6_2_Team03.domain.travelplan.repository.TravelPlanRepository;
import grepp.NBE5_6_2_Team03.domain.travelschedule.TravelRoute;
import grepp.NBE5_6_2_Team03.domain.travelschedule.TravelSchedule;
import grepp.NBE5_6_2_Team03.domain.travelschedule.repository.TravelScheduleRepository;
import grepp.NBE5_6_2_Team03.global.exception.NotFoundException;
import grepp.NBE5_6_2_Team03.global.message.ExceptionMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TravelScheduleService {

    private final TravelScheduleRepository travelScheduleRepository;
    private final TravelPlanRepository travelPlanRepository;
    private final TravelTimeAiService timeAiService;

    @Transactional
    public TravelSchedule createSchedule(Long travelPlanId, TravelScheduleRequest request) {
        TravelPlan plan = travelPlanRepository.findById(travelPlanId)
            .orElseThrow(() -> new NotFoundException(ExceptionMessage.PLANNED_NOT_FOUND));

        TravelRouteRequest currentTravelRouteRequest = request.getTravelRouteRequest();

        if (travelRouteExist(currentTravelRouteRequest)) {
            validateTravelSchedule(currentTravelRouteRequest.getDeparture(),
                currentTravelRouteRequest.getDestination(), currentTravelRouteRequest.getTransportation(),
                request.getTravelScheduleDate(), plan.getTravelStartDate(), plan.getTravelEndDate());
        }

        TravelSchedule schedule = request.toEntity(plan, request, timeAiService);
        return travelScheduleRepository.save(schedule);
    }

    @Transactional
    public TravelScheduleEditResponse editSchedule(Long travelScheduleId, TravelScheduleEditRequest request) {
        TravelSchedule schedule = travelScheduleRepository.findById(travelScheduleId)
            .orElseThrow(() -> new NotFoundException(ExceptionMessage.SCHEDULE_NOT_FOUND));

        TravelPlan plan = schedule.getTravelPlan();

        TravelRoute travelRoute = null;
        TravelRouteRequest currentTravelRouteRequest = request.getTravelRouteRequest();

        if (travelRouteExist(currentTravelRouteRequest)) {
            validateTravelSchedule(currentTravelRouteRequest.getDeparture(),
                currentTravelRouteRequest.getDestination(), currentTravelRouteRequest.getTransportation(),
                request.getTravelScheduleDate(), plan.getTravelStartDate(), plan.getTravelEndDate());

            travelRoute = currentTravelRouteRequest.toEntity(currentTravelRouteRequest, timeAiService);
        } else {
            travelRoute = schedule.getTravelRoute(); // 따로 요청 사항이 없다면 기존의 route 유지
        }

        schedule.edit(
            travelRoute,
            request.getContent(),
            request.getPlaceName(),
            request.getTravelScheduleDate(),
            request.getExpense()
        );

        return TravelScheduleEditResponse.fromEntity(schedule);
    }

    @Transactional
    public void deleteSchedule(Long travelScheduleId) {
        TravelSchedule schedule = travelScheduleRepository.findById(travelScheduleId)
            .orElseThrow(() -> new NotFoundException(ExceptionMessage.SCHEDULE_NOT_FOUND));

        travelScheduleRepository.delete(schedule);
    }

    @Transactional
    public TravelSchedule editScheduleStatus(Long travelScheduleId,
        TravelScheduleStatusRequest request) {
        TravelSchedule schedule = travelScheduleRepository.findById(travelScheduleId)
            .orElseThrow(() -> new NotFoundException(ExceptionMessage.SCHEDULE_NOT_FOUND));

        schedule.editStatus(request.getStatus());
        return schedule;
    }

    public GroupedTravelSchedulesResponse getGroupedSchedules(Long travelPlanId) {
        List<TravelSchedule> travelPlans = travelScheduleRepository.findByTravelPlanId(
            travelPlanId);
        return GroupedTravelSchedulesResponse.from(travelPlans);
    }

    public TravelSchedule findById(Long travelScheduleId) {
        return travelScheduleRepository.findByIdWithTravelPlan(travelScheduleId)
            .orElseThrow(() -> new NotFoundException(ExceptionMessage.SCHEDULE_NOT_FOUND));
    }

    private Boolean travelRouteExist(TravelRouteRequest request) {
        if (request == null) {
            return false;
        }
        return Stream.of(request.getDeparture(), request.getDestination(), request.getTransportation())
            .anyMatch(s -> s != null && !s.isBlank());
    }

    private void validateTravelSchedule(String departure, String destination, String transportation,
        LocalDateTime travelScheduleDate, LocalDate travelStartDate, LocalDate travelEndDate) {
        boolean departureExists = departure != null && !departure.isBlank();
        boolean destinationExists = destination != null && !destination.isBlank();
        boolean transportationExists = transportation != null && !transportation.isBlank();

        if (!(departureExists == destinationExists && destinationExists == transportationExists)) {
            throw new IllegalArgumentException("출발지, 도착지, 이동수단은 모두 입력하거나 모두 비워야 합니다.");
        }

        if (travelScheduleDate.toLocalDate().isBefore(travelStartDate)
            || travelScheduleDate.toLocalDate().isAfter(travelEndDate)) {
            throw new IllegalArgumentException("여행 일정 날짜는 여행 계획 날짜 안에 포함되어야 합니다.");
        }
    }

}
