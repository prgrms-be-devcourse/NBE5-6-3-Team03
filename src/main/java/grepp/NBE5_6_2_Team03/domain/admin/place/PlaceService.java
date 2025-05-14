package grepp.NBE5_6_2_Team03.domain.admin.place;

import grepp.NBE5_6_2_Team03.domain.admin.place.entity.Place;
import grepp.NBE5_6_2_Team03.domain.admin.place.repository.PlaceRepository;
import java.util.List;
import java.util.Optional;
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

    public List<Place> findAll() {
        return placeRepository.findAll();
    }

    public Place findById(String id) {
        return placeRepository.findByPlaceId(id);
    }

    public Set<String> getCounties(List<Place> places) {
        return places.stream()
            .map(Place::getCountry)
            .collect(Collectors.toCollection(TreeSet::new));
    }

    public Set<String> getCities(List<Place> places) {
        return places.stream()
            .map(Place::getCity)
            .collect(Collectors.toCollection(TreeSet::new));
    }

    public void updatePlace(String id, Place formPlace) {
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


}
