package grepp.NBE5_6_2_Team03.api.controller.admin.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
@AllArgsConstructor
public class CountriesStatisticResponse {

    private String country;
    private long count;

}
