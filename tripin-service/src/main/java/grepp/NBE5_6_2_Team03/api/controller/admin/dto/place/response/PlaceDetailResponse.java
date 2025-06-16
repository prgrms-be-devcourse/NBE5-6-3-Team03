package grepp.NBE5_6_2_Team03.api.controller.admin.dto.place.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PlaceDetailResponse {
    private PlaceInfoResponse place;
    private List<String> countries;
    private List<String> cities;

    public static PlaceDetailResponse of(PlaceInfoResponse place, List<String> countries, List<String> cities) {
        return PlaceDetailResponse.builder()
            .place(place)
            .countries(countries)
            .cities(cities)
            .build();
    }
}