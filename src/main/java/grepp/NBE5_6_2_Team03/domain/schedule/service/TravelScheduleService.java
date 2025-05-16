package grepp.NBE5_6_2_Team03.domain.schedule.service;

import grepp.NBE5_6_2_Team03.api.controller.schedule.dto.request.TravelScheduleRequest;
import grepp.NBE5_6_2_Team03.domain.plan.entity.TravelPlan;
import grepp.NBE5_6_2_Team03.domain.plan.repository.TravelPlanRepository;
import grepp.NBE5_6_2_Team03.domain.schedule.TravelSchedule;
import grepp.NBE5_6_2_Team03.domain.schedule.code.ScheduleStatus;
import grepp.NBE5_6_2_Team03.domain.schedule.repository.TravelScheduleRepository;
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

        TravelSchedule schedule = TravelSchedule.create(plan, request);
        travelScheduleRepository.save(schedule);
    }

    @Transactional
    public Long editSchedule(Long travelScheduleId, TravelScheduleRequest request) {
        TravelSchedule schedule = travelScheduleRepository.findById(travelScheduleId)
            .orElseThrow(() -> new NotFoundException(Message.SCHEDULE_NOT_FOUND));

        schedule.edit(
            request.getContent(),
            request.getPlaceName(),
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
