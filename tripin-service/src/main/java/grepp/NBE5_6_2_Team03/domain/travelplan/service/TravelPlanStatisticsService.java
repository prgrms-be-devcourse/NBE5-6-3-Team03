package grepp.NBE5_6_2_Team03.domain.travelplan.service;

import grepp.NBE5_6_2_Team03.api.controller.travelplan.dto.response.CountryCountView;
import grepp.NBE5_6_2_Team03.domain.travelplan.repository.TravelPlanStatisticsRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TravelPlanStatisticsService {

    private final TravelPlanStatisticsRepository travelPlanStatisticsRepository;

    public List<CountryCountView> getTopCountriesLastMonth() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        return travelPlanStatisticsRepository.findTopCountriesLastMonth(oneMonthAgo);
    }
}
