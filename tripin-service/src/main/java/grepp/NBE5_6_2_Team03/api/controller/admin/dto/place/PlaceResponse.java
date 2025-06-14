package grepp.NBE5_6_2_Team03.api.controller.admin.dto.place;

import grepp.NBE5_6_2_Team03.domain.place.entity.Place;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@Builder
public class PlaceResponse {
    private String placeId;
    private String country;
    private String city;
    private String placeName;
    private Double latitude;
    private Double longitude;

    public static PlaceResponse of(Place place) {
        return PlaceResponse.builder()
            .placeId(place.getPlaceId())
            .country(place.getCountry())
            .city(place.getCity())
            .placeName(place.getPlaceName())
            .latitude(place.getLatitude())
            .longitude(place.getLongitude())
            .build();
    }

}