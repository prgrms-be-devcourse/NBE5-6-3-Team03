package grepp.NBE5_6_2_Team03.domain.plan.service;

import grepp.NBE5_6_2_Team03.api.controller.plan.dto.TravelPlanDto;
import grepp.NBE5_6_2_Team03.domain.plan.entity.CountryStatus;
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

    public void createPlan(Long userid, TravelPlanDto planDto) {

        User user = userRepository.findById(userid)
            .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        CountryStatus status = CountryStatus.fromCountryName(planDto.getCountry());

        TravelPlan plan = TravelPlan.builder()
            .user(user)
            .countryStatus(status)
            .name(planDto.getName())
            .country(planDto.getCountry())
            .publicMoney(planDto.getPublicMoney())
            .count(planDto.getCount())
            .travelStartDate(planDto.getTravelStartDate())
            .travelEndDate(planDto.getTravelEndDate())
            .createdDateTime(LocalDateTime.now())
            .modifyDateTime(LocalDateTime.now())
            .build();

        travelPlanRepository.save(plan);
    }

    public TravelPlanDto getPlan(Long id) {
        TravelPlan plan = travelPlanRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 계획입니다."));

        return TravelPlanDto.builder()
            .name(plan.getName())
            .country(plan.getCountry())
            .publicMoney(plan.getPublicMoney())
            .count(plan.getCount())
            .travelStartDate(plan.getTravelStartDate())
            .travelEndDate(plan.getTravelEndDate())
            .build();
    }

    @Transactional
    public void updatePlan(Long id, TravelPlanDto planDto) {
        TravelPlan existingPlan = travelPlanRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("여행 계획을 찾을 수 없습니다."));

        CountryStatus status = CountryStatus.fromCountryName(planDto.getCountry());

        existingPlan.setCountryStatus(status);
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