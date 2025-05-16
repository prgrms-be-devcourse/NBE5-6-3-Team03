package grepp.NBE5_6_2_Team03.domain.schedule.repository;

import grepp.NBE5_6_2_Team03.domain.plan.TravelPlan;
import grepp.NBE5_6_2_Team03.domain.schedule.TravelSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TravelScheduleRepository extends JpaRepository<TravelSchedule, Long> {

    List<TravelSchedule> findByTravelPlan(TravelPlan travelPlan);

    @Query("SELECT s FROM TravelSchedule s " +
        "WHERE s.travelPlan = :plan " +
        "ORDER BY s.travelScheduleDate ASC, s.scheduleStatus DESC")
    List<TravelSchedule> findSortedSchedules(@Param("plan") TravelPlan plan);
}
