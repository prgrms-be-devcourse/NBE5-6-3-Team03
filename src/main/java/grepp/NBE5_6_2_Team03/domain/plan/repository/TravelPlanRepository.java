package grepp.NBE5_6_2_Team03.domain.plan.repository;

import grepp.NBE5_6_2_Team03.domain.plan.entity.TravelPlan;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelPlanRepository extends JpaRepository<TravelPlan, Long> {

    List<TravelPlan> findByUserId(Long userId);
}
