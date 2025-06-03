package grepp.NBE5_6_2_Team03.domain.place;

import grepp.NBE5_6_2_Team03.api.controller.admin.dto.place.PlaceRequest;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.place.PlaceResponse;
import grepp.NBE5_6_2_Team03.domain.place.entity.Place;
import grepp.NBE5_6_2_Team03.domain.place.repository.PlaceRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    public List<PlaceResponse> findAll() {
        List<Place> places =  placeRepository.findAll();
        return places.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public PlaceResponse findById(String id) {
        Place place = placeRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 ID의 장소를 찾을 수 없습니다: " + id));
        return convertToDto(place);
    }

    public List<String> getCountries() {
       return placeRepository.findDistinctCountries();
    }

    public List<String> getCities() {
        return placeRepository.findDistinctCities();
    }

    @Transactional
    public void updatePlace(String id, PlaceRequest formPlace) {
        Place place = placeRepository.findByPlaceId(id);
        if (place == null) {
            throw new IllegalArgumentException("해당 ID의 장소를 찾을 수 없습니다: " + id);
        }

        // TODO 방어적 복사 개념 공부하기
        place.update(
            formPlace.getCity(),
            formPlace.getCountry(),
            formPlace.getPlaceName(),
            formPlace.getLatitude(),
            formPlace.getLongitude()
        );

        //STUDY Transactional 어노테이션을 사용하면 종료시점에 자동으로 커밋을 update 쿼리로 날려준다.
    }

    @Transactional
    public void deleteById(String id) {
        Place place = placeRepository.findByPlaceId(id);
        if (place == null) {
            throw new IllegalArgumentException("해당 장소를 찾을 수 없습니다: " + id);
        }
        placeRepository.delete(place);
    }

    private PlaceResponse convertToDto(Place place) {
        return PlaceResponse.fromEntity(
            place.getPlaceId(),
            place.getCountry(),
            place.getCity(),
            place.getPlaceName(),
            place.getLatitude(),
            place.getLongitude());
    }

}
