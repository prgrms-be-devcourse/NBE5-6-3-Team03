package grepp.NBE5_6_2_Team03.domain.travelplan.repository;

import grepp.NBE5_6_2_Team03.api.controller.admin.dto.statistic.CountriesStatisticResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.statistic.MonthlyStatisticProjection;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.statistic.MonthlyStatisticResponse;
import grepp.NBE5_6_2_Team03.domain.travelplan.TravelPlan;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TravelPlanRepository extends JpaRepository<TravelPlan, Long> {

    List<TravelPlan> findByUserId(Long userId);

    // CountriesStatisticResponse는 JPQL DTO Projection 사용 (이미 제공된 DTO 사용)
    @Query("SELECT NEW grepp.NBE5_6_2_Team03.api.controller.admin.dto.statistic.CountriesStatisticResponse(t.country, COUNT(t)) FROM TravelPlan t GROUP BY t.country")
    List<CountriesStatisticResponse> getCountriesStatistics();

    // MonthlyStatisticProjection (인터페이스 기반 Projection) 사용
    // 네이티브 쿼리에서 컬럼에 명시적으로 별칭을 부여해야 합니다.
    @Query(value = "select month(t.travel_start_date) as month, count(*) as count from travel_plan t"
        + " where year(t.travel_start_date) = year(CURDATE()) "
        + "group by month(t.travel_start_date) order by month(t.travel_start_date)", nativeQuery = true)
    List<MonthlyStatisticProjection> getMonthStatistics();

    long deleteByUserId(Long userId);
}
