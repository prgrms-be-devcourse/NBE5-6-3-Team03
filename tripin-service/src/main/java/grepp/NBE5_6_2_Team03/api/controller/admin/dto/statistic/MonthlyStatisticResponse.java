package grepp.NBE5_6_2_Team03.api.controller.admin.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MonthlyStatisticResponse {

    int month;
    long count;

}
