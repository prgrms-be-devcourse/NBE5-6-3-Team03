package grepp.NBE5_6_2_Team03.domain.plan.service;

import grepp.NBE5_6_2_Team03.api.controller.plan.dto.TravelPlanDto;
import grepp.NBE5_6_2_Team03.domain.plan.entity.TravelPlan;
import grepp.NBE5_6_2_Team03.domain.plan.repository.TravelPlanRepository;
import grepp.NBE5_6_2_Team03.domain.user.User;
import grepp.NBE5_6_2_Team03.domain.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TravelPlanService {

    private final TravelPlanRepository travelPlanRepository;
    private final UserRepository userRepository;


    public List<TravelPlan> getPlansByUser() {

        List<TravelPlan> plans = travelPlanRepository.findByUserId(1L);
        return plans;
    }

    public void createPlan(TravelPlanDto planDto) {
        TravelPlan plan = new TravelPlan();
        User user = userRepository.findById(1L)
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
}

