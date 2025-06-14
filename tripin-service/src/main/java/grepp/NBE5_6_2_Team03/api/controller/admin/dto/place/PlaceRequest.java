package grepp.NBE5_6_2_Team03.api.controller.admin.dto.place;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlaceRequest {
    private String placeId;
    private String country;
    private String city;
    private String placeName;
    private Double latitude;
    private Double longitude;

}