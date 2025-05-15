package grepp.NBE5_6_2_Team03.domain.plan.service;

import grepp.NBE5_6_2_Team03.api.controller.plan.dto.TravelPlanDto;
import grepp.NBE5_6_2_Team03.domain.plan.entity.TravelPlan;
import grepp.NBE5_6_2_Team03.domain.plan.repository.TravelPlanRepository;
import grepp.NBE5_6_2_Team03.domain.user.User;
import grepp.NBE5_6_2_Team03.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TravelPlanService {

    private final TravelPlanRepository travelPlanRepository;
    private final UserRepository userRepository;


    public List<TravelPlan> getPlansByUser(Long userid) {

        List<TravelPlan> plans = travelPlanRepository.findByUserId(userid);
        return plans;
    }


    public void createPlan(Long userid,TravelPlanDto planDto) {
        TravelPlan plan = new TravelPlan();
        User user = userRepository.findById(userid)
            .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        plan.setUser(user);
        plan.setName(planDto.getName());
        plan.setCountry(planDto.getCountry());
        plan.setPublicMoney(planDto.getPublicMoney());
        plan.setCount(planDto.getCount());
        plan.setTravelStartDate(planDto.getTravelStartDate());
        plan.setTravelEndDate(planDto.getTravelEndDate());
        plan.setCreatedDateTime(LocalDateTime.now());
        plan.setModifyDateTime(LocalDateTime.now());

        travelPlanRepository.save(plan);

    }

    public TravelPlanDto getPlan(Long id) {
        TravelPlan plan = travelPlanRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 계획입니다."));

        TravelPlanDto dto = new TravelPlanDto();
        dto.setName(plan.getName());
        dto.setCountry(plan.getCountry());
        dto.setPublicMoney(plan.getPublicMoney());
        dto.setCount(plan.getCount());
        dto.setTravelStartDate(plan.getTravelStartDate());
        dto.setTravelEndDate(plan.getTravelEndDate());

        return dto;
    }

    @Transactional
    public void updatePlan(Long id, TravelPlanDto planDto) {
        TravelPlan existingPlan = travelPlanRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("여행 계획을 찾을 수 없습니다."));

        existingPlan.setName(planDto.getName());
        existingPlan.setCountry(planDto.getCountry());
        existingPlan.setPublicMoney(planDto.getPublicMoney());
        existingPlan.setCount(planDto.getCount());
        existingPlan.setTravelStartDate(planDto.getTravelStartDate());
        existingPlan.setTravelEndDate(planDto.getTravelEndDate());
        existingPlan.setModifyDateTime(LocalDateTime.now());

        travelPlanRepository.save(existingPlan);
    }


    @Transactional
    public void deletePlan(Long id) {
        TravelPlan plan = travelPlanRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 계획이 존재하지 않습니다."));

        travelPlanRepository.delete(plan);
    }
}

