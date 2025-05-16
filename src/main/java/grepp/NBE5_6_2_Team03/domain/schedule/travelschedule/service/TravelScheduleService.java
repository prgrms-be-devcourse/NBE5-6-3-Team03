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

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelScheduleService {

    private final TravelScheduleRepository travelScheduleRepository;
    private final TravelPlanRepository travelPlanRepository;

    public void addSchedule(Long travelPlanId, TravelScheduleRequest request) {
        TravelPlan plan = travelPlanRepository.findById(travelPlanId)
            .orElseThrow(() -> new NotFoundException(Message.PLANNED_NOT_FOUND));

        TravelSchedule schedule = TravelSchedule.builder()
            .travelPlan(plan)
            .content(request.getContent())
            .placeName(request.getPlaceName())
            .scheduleStatus(ScheduleStatus.PLANNED)
            .travelScheduleDate(request.getTravelScheduleDate())
            .createdDateTime(LocalDateTime.now())
            .build();

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

        schedule.updateStatus();
    }

    public List<TravelSchedule> getSchedulesByPlanId(Long travelPlanId) {
        TravelPlan plan = travelPlanRepository.findById(travelPlanId)
            .orElseThrow(() -> new NotFoundException(Message.PLANNED_NOT_FOUND));

        return travelScheduleRepository.findByTravelPlan(plan);
    }

    public TravelSchedule findById(Long travelScheduleId) {
        return travelScheduleRepository.findById(travelScheduleId)
            .orElseThrow(() -> new NotFoundException(Message.SCHEDULE_NOT_FOUND));
    }
}
