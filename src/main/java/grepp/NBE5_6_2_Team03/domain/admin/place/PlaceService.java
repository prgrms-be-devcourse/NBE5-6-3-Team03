package grepp.NBE5_6_2_Team03.domain.admin.place;

import grepp.NBE5_6_2_Team03.api.controller.admin.place.dto.PlaceDto;
import grepp.NBE5_6_2_Team03.domain.admin.place.entity.Place;
import grepp.NBE5_6_2_Team03.domain.admin.place.repository.PlaceRepository;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PlaceService {
    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public List<PlaceDto> findAll() {
        List<Place> places =  placeRepository.findAll();
        return places.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public PlaceDto findById(String id) {
        Place place = placeRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 ID의 장소를 찾을 수 없습니다: " + id));
        return convertToDto(place);
    }

    public Set<String> getCounties(List<PlaceDto> places) {
        return places.stream()
            .map(PlaceDto::getCountry)
            .collect(Collectors.toCollection(TreeSet::new));
    }

    public Set<String> getCities(List<PlaceDto> places) {
        return places.stream()
            .map(PlaceDto::getCity)
            .collect(Collectors.toCollection(TreeSet::new));
    }

    public void updatePlace(String id, PlaceDto formPlace) {
        Place place = placeRepository.findByPlaceId(id);
        if (place == null) {
            throw new IllegalArgumentException("해당 ID의 장소를 찾을 수 없습니다: " + id);
        }

        place.setCity(formPlace.getCity());
        place.setCountry(formPlace.getCountry());
        place.setPlaceName(formPlace.getPlaceName());
        place.setLatitude(formPlace.getLatitude());
        place.setLongitude(formPlace.getLongitude());

        placeRepository.save(place);
    }

    public void deleteById(String id) {
        Place place = placeRepository.findByPlaceId(id);
        if (place == null) {
            throw new IllegalArgumentException("해당 장소를 찾을 수 없습니다: " + id);
        }
        placeRepository.delete(place);
    }

    private PlaceDto convertToDto(Place place) {
        return new PlaceDto(
            place.getPlaceId(),
            place.getCountry(),
            place.getCity(),
            place.getPlaceName(),
            place.getLatitude(),
            place.getLongitude()
        );
    }


}
