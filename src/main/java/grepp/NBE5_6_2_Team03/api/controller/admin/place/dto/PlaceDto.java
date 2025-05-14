package grepp.NBE5_6_2_Team03.api.controller.admin.place.dto;

import grepp.NBE5_6_2_Team03.domain.admin.place.entity.Place;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class PlaceDto {
    private String placeId;
    private String country;
    private String city;
    private String placeName;
    private Double latitude;
    private Double longitude;

}