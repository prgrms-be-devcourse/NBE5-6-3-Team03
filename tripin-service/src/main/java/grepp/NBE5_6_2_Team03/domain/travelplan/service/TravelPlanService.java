package grepp.NBE5_6_2_Team03.domain.travelplan.service;

import grepp.NBE5_6_2_Team03.api.controller.travelplan.dto.request.TravelPlanEditRequest;
import grepp.NBE5_6_2_Team03.api.controller.travelplan.dto.request.TravelPlanSaveRequest;
import grepp.NBE5_6_2_Team03.api.controller.travelplan.dto.response.TravelPlanInfo;
import grepp.NBE5_6_2_Team03.api.controller.travelplan.dto.response.TravelPlansResponse;
import grepp.NBE5_6_2_Team03.domain.travelplan.TravelPlan;
import grepp.NBE5_6_2_Team03.domain.travelplan.repository.TravelPlanRepository;
import grepp.NBE5_6_2_Team03.domain.user.User;
import grepp.NBE5_6_2_Team03.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TravelPlanService {

    private final TravelPlanRepository travelPlanRepository;
    private final UserRepository userRepository;

    public TravelPlanInfo getMyPlan(Long id) {
        TravelPlan plan = travelPlanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 계획입니다."));

        return TravelPlanInfo.of(plan);
    }

    public TravelPlansResponse getMyPlans(Long userid, String username) {
        List<TravelPlan> plans = travelPlanRepository.findByUserId(userid);
        List<TravelPlanInfo> responses  = plans.stream()
                .map(TravelPlanInfo::of)
                .toList();

        return new TravelPlansResponse(username, responses);
    }

    @Transactional
    public Long create(TravelPlanSaveRequest request, Long userid) {
        validateTravelPlan(request.getTravelStartDate(), request.getTravelEndDate());

        User user = userRepository.findById(userid)
            .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        return travelPlanRepository.save(request.toEntity(user)).getId();
    }

    @Transactional
    public Long modifyPlan(TravelPlanEditRequest request, Long id) {
        TravelPlan plan = travelPlanRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("여행 계획을 찾을 수 없습니다."));

        validateTravelPlan(request.getTravelStartDate(), request.getTravelEndDate());
        modifyPlan(request, plan);
        return plan.getId();
    }

    @Transactional
    public void deletePlan(Long id) {
        TravelPlan plan = travelPlanRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 계획이 존재하지 않습니다."));

        travelPlanRepository.delete(plan);
    }

    private void modifyPlan(TravelPlanEditRequest request, TravelPlan plan) {
        plan.modify(
                request.getName(),
                request.getCountry(),
                request.getApplicants(),
                request.getCurrentUnit(),
                request.getPublicMoney(),
                request.getTravelStartDate(),
                request.getTravelEndDate()
        );
    }

    private void validateTravelPlan(LocalDate travelStartDate, LocalDate travelEndDate) {
        if (travelStartDate.isAfter(travelEndDate)) {
            throw new IllegalArgumentException("여행 시작일은 여행 종료일보다 빠르거나 같아야 합니다.");
        }
    }
}