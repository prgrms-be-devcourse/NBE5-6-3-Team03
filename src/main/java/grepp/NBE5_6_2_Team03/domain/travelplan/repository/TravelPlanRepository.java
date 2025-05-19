package grepp.NBE5_6_2_Team03.domain.travelplan.repository;

import grepp.NBE5_6_2_Team03.domain.travelplan.TravelPlan;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TravelPlanRepository extends JpaRepository<TravelPlan, Long> {

    List<TravelPlan> findByUserId(Long userId);

    @Query("select t.country, count(*) from TravelPlan t group by t.country")
    List<Object[]> getCountriesStatistics();

    @Query(value = "select month(t.travel_start_date), count(*) from travel_plan t"
        + " where year(t.travel_start_date) = year(CURDATE()) "
        + "group by month(t.travel_start_date) order by month(t.travel_start_date)", nativeQuery = true)
    List<Object[]> getMonthStatistics();

    long deleteByUserId(Long userId);
}
