package grepp.NBE5_6_2_Team03.domain.map;

import grepp.NBE5_6_2_Team03.api.controller.map.dto.MapResponse;
import grepp.NBE5_6_2_Team03.domain.place.entity.Place;
import grepp.NBE5_6_2_Team03.domain.place.repository.CountryRepository;
import grepp.NBE5_6_2_Team03.domain.place.repository.PlaceRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MapService {

    private final CountryRepository countryRepository;
    private final PlaceRepository placeRepository;

    public List<String> getCity(String country) {
        return countryRepository.findCityByCountry(country);
    }

    public List<String> getCountries() {
        return countryRepository.findAllCountries();
    }

    public List<String> getAllCities() {
        return countryRepository.findAllCities();
    }

    public MapResponse getPlace(String placeId) {
        Place place = placeRepository.findByPlaceId(placeId);
        return convertToResponse(place);
    }

    public Page<MapResponse> findPlaces(String country, String city, Pageable pageable) {
        Page<Place> places;

        switch (hasFilterOption(country, city)){
            case "country" -> places = placeRepository.findByCountry(country, pageable);
            case "city" -> places = placeRepository.findByCity(city, pageable);
            default -> places = placeRepository.findAll(pageable);
        }

        return places.map(this::convertToResponse);
    }

    public Optional<String> getCountryByCity(String city) {
        return placeRepository.findCountryByCity(city);
    }

    private String hasFilterOption(String country, String city) {
        if(city != null && !city.isEmpty()) return "city";
        if(country != null && !country.isEmpty()) return "country";
        return "none";
    }

    private MapResponse convertToResponse(Place place) {
        return MapResponse.builder()
            .id(place.getPlaceId())
            .country(place.getCountry())
            .city(place.getCity())
            .placeName(place.getPlaceName())
            .latitude(place.getLatitude())
            .longitude(place.getLongitude())
            .build();
    }
}
