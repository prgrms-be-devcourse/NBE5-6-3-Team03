package grepp.NBE5_6_2_Team03.api.controller.admin.place.dto;

import grepp.NBE5_6_2_Team03.domain.admin.place.entity.Country;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class CountryResponse {
    private String city;
    private Double latitude;
    private Double longitude;
    private int radius;

    public CountryResponse(String city, Double latitude, Double longitude, int radius) {
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }

    public static CountryResponse from(Country country) {
        return new CountryResponse(
            country.getCityName(),
            country.getLatitude(),
            country.getLongitude(),
            country.getCityRange()
        );
    }

    public static List<CountryResponse> fromList(List<Country> cities) {
        return cities.stream()
            .map(CountryResponse::from)
            .collect(Collectors.toList());
    }

}