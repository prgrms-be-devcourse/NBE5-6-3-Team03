package grepp.NBE5_6_2_Team03.domain.travelplan.repository;

import grepp.NBE5_6_2_Team03.api.controller.travelplan.dto.response.CountryCountView;
import grepp.NBE5_6_2_Team03.domain.travelplan.TravelPlan;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TravelPlanStatisticsRepository extends JpaRepository<TravelPlan, Long> {

    @Query("SELECT tp.country AS country, COUNT(tp) AS count " +
        "FROM TravelPlan tp " +
        "WHERE tp.createdDateTime >= :lastMonth " +
        "GROUP BY tp.country " +
        "ORDER BY COUNT(tp) DESC")
    List<CountryCountView> findTopCountriesLastMonth(@Param("lastMonth") LocalDateTime lastMonth);
}
