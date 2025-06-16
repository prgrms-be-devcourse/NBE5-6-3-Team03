package grepp.NBE5_6_2_Team03.api.controller.admin.dto.place.response;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class CountryCityInfo {
    private List<String> countries;
    private List<String> cities;

    public static CountryCityInfo of(List<String> countries, List<String> cities) {
        return CountryCityInfo.builder()
            .countries(countries)
            .cities(cities)
            .build();
    }
}
