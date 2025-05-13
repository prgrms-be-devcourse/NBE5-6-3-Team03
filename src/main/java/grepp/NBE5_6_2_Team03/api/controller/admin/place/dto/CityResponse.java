package grepp.NBE5_6_2_Team03.api.controller.admin.place.dto;

import grepp.NBE5_6_2_Team03.domain.admin.place.entity.City;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class CityResponse {
    private String city;
    private Double latitude;
    private Double longitude;
    private int radius;

    public CityResponse(String city, Double latitude, Double longitude, int radius) {
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }

    public static CityResponse from(City city) {
        return new CityResponse(
            city.getCityName(),
            city.getLatitude(),
            city.getLongitude(),
            city.getCityRange()
        );
    }

    /**
     * cities 를 받아 LIST<Response> 를 반환
     */
    public static List<CityResponse> fromList(List<City> cities) {
        return cities.stream()
            .map(CityResponse::from)
            .collect(Collectors.toList());
    }

}