package grepp.NBE5_6_2_Team03.domain.admin.place;

import grepp.NBE5_6_2_Team03.api.controller.admin.place.dto.CityResponse;
import grepp.NBE5_6_2_Team03.domain.admin.place.entity.City;
import grepp.NBE5_6_2_Team03.domain.admin.place.repository.CityRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;

    public City getCityEntity(String cityName) {
        return cityRepository.findByCityName(cityName)
            .orElseThrow(() -> new IllegalArgumentException("City not found"));
    }

    public CityResponse toDto(String cityName) {
        City city = getCityEntity(cityName);
        return new CityResponse(
            city.getCityName(),
            city.getLatitude(),
            city.getLongitude(),
            city.getCityRange()
        );
    }

    public List<CityResponse> getAllCities() {
        List<City> cities = cityRepository.findAll();
        return CityResponse.fromList(cities);
    }


}
