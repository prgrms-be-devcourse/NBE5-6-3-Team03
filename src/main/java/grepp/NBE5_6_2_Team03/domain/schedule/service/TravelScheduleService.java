package grepp.NBE5_6_2_Team03.domain.schedule.service;

import grepp.NBE5_6_2_Team03.api.controller.schedule.dto.request.TravelScheduleRequest;
import grepp.NBE5_6_2_Team03.domain.plan.TravelPlan;
import grepp.NBE5_6_2_Team03.domain.plan.repository.TravelPlanRepository;
import grepp.NBE5_6_2_Team03.domain.schedule.TravelSchedule;
import grepp.NBE5_6_2_Team03.domain.schedule.code.ScheduleStatus;
import grepp.NBE5_6_2_Team03.domain.schedule.repository.TravelScheduleRepository;
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
            .orElseThrow(() -> new IllegalArgumentException("해당 여행 계획이 존재하지 않습니다."));

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
    public Long updateSchedule(Long travelScheduleId, TravelScheduleRequest request) {
        TravelSchedule schedule = travelScheduleRepository.findById(travelScheduleId)
            .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));

        schedule.setContent(request.getContent());
        schedule.setPlaceName(request.getPlaceName());
        schedule.setTravelScheduleDate(request.getTravelScheduleDate());
        schedule.setModifiedDateTime(LocalDateTime.now());

        return schedule.getTravelPlan().getTravelPlanId();
    }

    @Transactional
    public void deleteSchedule(Long travelScheduleId) {
        TravelSchedule schedule = travelScheduleRepository.findById(travelScheduleId)
            .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));

        travelScheduleRepository.delete(schedule);
    }

    @Transactional
    public void scheduleStatus(Long travelScheduleId) {
        TravelSchedule schedule = travelScheduleRepository.findById(travelScheduleId)
            .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));

        schedule.updateStatus();
    }

    public List<TravelSchedule> getSchedulesByPlanId(Long travelPlanId) {
        TravelPlan plan = travelPlanRepository.findById(travelPlanId)
            .orElseThrow(() -> new IllegalArgumentException("해당 여행 계획이 존재하지 않습니다."));

        return travelScheduleRepository.findByTravelPlan(plan);
    }

    public TravelSchedule findById(Long travelScheduleId) {
        return travelScheduleRepository.findById(travelScheduleId)
            .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));
    }
}
