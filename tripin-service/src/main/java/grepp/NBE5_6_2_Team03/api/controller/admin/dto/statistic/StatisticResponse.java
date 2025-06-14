package grepp.NBE5_6_2_Team03.api.controller.admin.dto.statistic;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class StatisticResponse {
    private List<CountriesStatisticResponse> countries;
    private List<MonthlyStatisticResponse> monthly;
}
