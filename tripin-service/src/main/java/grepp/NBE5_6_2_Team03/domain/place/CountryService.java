package grepp.NBE5_6_2_Team03.domain.place;

import grepp.NBE5_6_2_Team03.api.controller.admin.dto.place.CountryResponse;
import grepp.NBE5_6_2_Team03.domain.place.entity.Country;
import grepp.NBE5_6_2_Team03.domain.place.repository.CountryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;

    public Country getCityEntity(String cityName) {
        return countryRepository.findByCityName(cityName)
            .orElseThrow(() -> new IllegalArgumentException("City not found"));
    }

    public CountryResponse getCountryResponse(String cityName) {
        Country country = getCityEntity(cityName);
        return CountryResponse.of(country);
    }

    public List<CountryResponse> getAllCities() {
        List<Country> cities = countryRepository.findAll();
        return CountryResponse.fromList(cities);
    }

}
