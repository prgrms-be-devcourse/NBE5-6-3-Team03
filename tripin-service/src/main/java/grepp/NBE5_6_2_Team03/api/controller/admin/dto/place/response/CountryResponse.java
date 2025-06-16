package grepp.NBE5_6_2_Team03.api.controller.admin.dto.place.response;

import grepp.NBE5_6_2_Team03.domain.place.entity.Country;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class CountryResponse {
    private final String city;
    private final Double latitude;
    private final Double longitude;
    private final int radius;

    public CountryResponse(String city, Double latitude, Double longitude, int radius) {
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }

    public static CountryResponse of(Country country) {
        return new CountryResponse(
            country.getCityName(),
            country.getLatitude(),
            country.getLongitude(),
            country.getCityRange()
        );
    }

    public static List<CountryResponse> fromList(List<Country> cities) {
        return cities.stream()
            .map(CountryResponse::of)
            .collect(Collectors.toList());
    }

}