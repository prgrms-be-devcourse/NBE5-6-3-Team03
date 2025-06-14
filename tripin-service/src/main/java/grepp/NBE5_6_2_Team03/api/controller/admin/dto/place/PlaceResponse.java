package grepp.NBE5_6_2_Team03.api.controller.admin.dto.place;

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

    public static PlaceResponse fromEntity(String placeId, String country, String city, String placeName, double latitude, double longitude) {
        return PlaceResponse.builder()
            .placeId(placeId)
            .country(country)
            .city(city)
            .placeName(placeName)
            .latitude(latitude)
            .longitude(longitude)
            .build();
    }

}