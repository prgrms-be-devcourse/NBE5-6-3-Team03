package grepp.NBE5_6_2_Team03.domain.schedule.travelschedule.service;

import grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule.dto.request.TravelScheduleRequest;
import grepp.NBE5_6_2_Team03.domain.travelplan.TravelPlan;
import grepp.NBE5_6_2_Team03.domain.travelplan.repository.TravelPlanRepository;
import grepp.NBE5_6_2_Team03.domain.schedule.travelschedule.TravelSchedule;
import grepp.NBE5_6_2_Team03.domain.schedule.travelschedule.code.ScheduleStatus;
import grepp.NBE5_6_2_Team03.domain.schedule.travelschedule.repository.TravelScheduleRepository;
import grepp.NBE5_6_2_Team03.global.exception.Message;
import grepp.NBE5_6_2_Team03.global.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TravelScheduleService {

    private final TravelScheduleRepository travelScheduleRepository;
    private final TravelPlanRepository travelPlanRepository;

    public void addSchedule(Long travelPlanId, TravelScheduleRequest request) {
        TravelPlan plan = travelPlanRepository.findById(travelPlanId)
            .orElseThrow(() -> new NotFoundException(Message.PLANNED_NOT_FOUND));

        validateLocationFields(request.getDeparture(), request.getDestination(), request.getTransportation());

        TravelSchedule schedule = request.toEntity(plan);
        travelScheduleRepository.save(schedule);
    }

    public void validateLocationFields(String departure, String destination, String transportation) {
        boolean departureExists = departure != null && !departure.isBlank();
        boolean destinationExists = destination != null && !destination.isBlank();
        boolean transportationExists = transportation != null && !transportation.isBlank();

        if (!(departureExists == destinationExists && destinationExists == transportationExists)) {
            throw new IllegalArgumentException("출발지, 도착지, 이동수단은 모두 입력하거나 모두 비워야 합니다.");
        }
    }

    @Transactional
    public Long editSchedule(Long travelScheduleId, TravelScheduleRequest request) {
        TravelSchedule schedule = travelScheduleRepository.findById(travelScheduleId)
            .orElseThrow(() -> new NotFoundException(Message.SCHEDULE_NOT_FOUND));

        schedule.edit(
            request.getContent(),
            request.getPlaceName(),
            request.getDeparture(),
            request.getDestination(),
            request.getTransportation(),
            request.getTravelScheduleDate()
        );

        return schedule.getTravelPlan().getTravelPlanId();
    }

    @Transactional
    public void deleteSchedule(Long travelScheduleId) {
        TravelSchedule schedule = travelScheduleRepository.findById(travelScheduleId)
            .orElseThrow(() -> new NotFoundException(Message.SCHEDULE_NOT_FOUND));

        travelScheduleRepository.delete(schedule);
    }

    @Transactional
    public void scheduleStatus(Long travelScheduleId) {
        TravelSchedule schedule = travelScheduleRepository.findById(travelScheduleId)
            .orElseThrow(() -> new NotFoundException(Message.SCHEDULE_NOT_FOUND));

        schedule.toggleStatus();
    }

    public List<TravelSchedule> getSchedulesByPlanId(Long travelPlanId) {
        TravelPlan plan = travelPlanRepository.findById(travelPlanId)
            .orElseThrow(() -> new NotFoundException(Message.PLANNED_NOT_FOUND));

        return travelScheduleRepository.findSortedSchedules(plan);
    }

    public Map<LocalDate, Map<ScheduleStatus, List<TravelSchedule>>> getGroupedSchedules(Long travelPlanId) {
        TravelPlan plan = travelPlanRepository.findById(travelPlanId)
            .orElseThrow(() -> new NotFoundException(Message.PLANNED_NOT_FOUND));

        List<TravelSchedule> schedules = travelScheduleRepository.findSortedSchedules(plan);
        Map<LocalDate, Map<ScheduleStatus, List<TravelSchedule>>> groupedSchedules = new LinkedHashMap<>();

        for (TravelSchedule schedule : schedules) {
            LocalDate date = schedule.getTravelScheduleDate();
            ScheduleStatus status = schedule.getScheduleStatus();

            groupedSchedules.putIfAbsent(date, new LinkedHashMap<>());
            groupedSchedules.get(date).putIfAbsent(status, new ArrayList<>());
            groupedSchedules.get(date).get(status).add(schedule);
        }

        return groupedSchedules;
    }

    public TravelSchedule findById(Long travelScheduleId) {
        return travelScheduleRepository.findById(travelScheduleId)
            .orElseThrow(() -> new NotFoundException(Message.SCHEDULE_NOT_FOUND));
    }
}
