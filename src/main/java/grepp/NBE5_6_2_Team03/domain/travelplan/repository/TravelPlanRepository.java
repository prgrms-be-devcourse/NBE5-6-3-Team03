package grepp.NBE5_6_2_Team03.domain.travelplan.repository;

import grepp.NBE5_6_2_Team03.domain.travelplan.TravelPlan;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelPlanRepository extends JpaRepository<TravelPlan, Long> {

    List<TravelPlan> findByUserId(Long userId);
}
