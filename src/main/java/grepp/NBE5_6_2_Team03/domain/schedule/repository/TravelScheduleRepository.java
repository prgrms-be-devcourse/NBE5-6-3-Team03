package grepp.NBE5_6_2_Team03.domain.schedule.repository;

import grepp.NBE5_6_2_Team03.domain.travelplan.TravelPlan;
import grepp.NBE5_6_2_Team03.domain.schedule.TravelSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelScheduleRepository extends JpaRepository<TravelSchedule, Long> {

    List<TravelSchedule> findByTravelPlan(TravelPlan travelPlan);
}
