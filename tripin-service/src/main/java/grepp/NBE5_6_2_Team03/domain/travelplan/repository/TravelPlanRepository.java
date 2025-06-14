package grepp.NBE5_6_2_Team03.domain.travelplan.repository;

import grepp.NBE5_6_2_Team03.api.controller.admin.dto.statistic.CountriesStatisticResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.statistic.MonthlyStatisticProjection;
import grepp.NBE5_6_2_Team03.domain.travelplan.TravelPlan;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TravelPlanRepository extends JpaRepository<TravelPlan, Long> {

    List<TravelPlan> findByUserId(Long userId);


    // fixme QueryDSL로 변경
    @Query("SELECT NEW CountriesStatisticResponse(t.country, COUNT(t)) FROM TravelPlan t GROUP BY t.country")
    List<CountriesStatisticResponse> getCountriesStatistics();

    @Query(value = "select month(t.travel_start_date) as month, count(*) as count from travel_plan t"
        + " where year(t.travel_start_date) = year(CURDATE()) "
        + "group by month(t.travel_start_date) order by month(t.travel_start_date)", nativeQuery = true)
    List<MonthlyStatisticProjection> getMonthStatistics();

    long deleteByUserId(Long userId);
}
