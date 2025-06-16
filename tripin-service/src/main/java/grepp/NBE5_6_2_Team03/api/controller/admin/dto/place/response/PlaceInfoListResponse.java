package grepp.NBE5_6_2_Team03.api.controller.admin.dto.place.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@Builder
@AllArgsConstructor
public class PlaceInfoListResponse {

    private Page<PlaceInfoResponse> places;
    private List<String> countries;
    private List<String> cities;

    public static PlaceInfoListResponse of(Page<PlaceInfoResponse> places, List<String> countries, List<String> cities) {
        return PlaceInfoListResponse.builder()
            .places(places)
            .countries(countries)
            .cities(cities)
            .build();
    }

}
