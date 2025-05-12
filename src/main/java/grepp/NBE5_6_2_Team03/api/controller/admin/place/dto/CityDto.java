package grepp.NBE5_6_2_Team03.api.controller.admin.place.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@NoArgsConstructor
public class CityDto {
    private String city;
    private Double latitude;
    private Double longitude;
    private int radius;

    public CityDto(String city, Double latitude, Double longitude, int radius) {
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }

}