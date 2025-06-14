package grepp.NBE5_6_2_Team03.domain.place;

import grepp.NBE5_6_2_Team03.api.controller.admin.dto.place.PlaceRequest;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.place.PlaceResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.place.PlaceSearchRequest;
import grepp.NBE5_6_2_Team03.domain.place.entity.Place;
import grepp.NBE5_6_2_Team03.domain.place.repository.PlaceQueryRepository;
import grepp.NBE5_6_2_Team03.domain.place.repository.PlaceRepository;
import grepp.NBE5_6_2_Team03.global.message.ExceptionMessage;
import grepp.NBE5_6_2_Team03.global.exception.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final PlaceQueryRepository placeQueryRepository;

    public List<PlaceResponse> findAll() {
        List<Place> places =  placeRepository.findAll();
        return places.stream().map(PlaceResponse::of).collect(Collectors.toList());
    }

    public Page<PlaceResponse> findPlacesPageable(PlaceSearchRequest req) {
        Page<Place> places = placeQueryRepository.findPaged(req.getCountry(), req.getCity(), req.getPageable());
        log.debug("Found {} places", places.getTotalElements());
        return places.map(PlaceResponse::of);
    }

    public PlaceResponse findById(String id) {
        Place place = placeRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(ExceptionMessage.PLACE_NOT_FOUND));
        return PlaceResponse.of(place);
    }

    public List<String> getCountries() {
       return placeRepository.findDistinctCountries();
    }

    public List<String> getCities() {
        return placeRepository.findDistinctCities();
    }

    @Transactional
    public void updatePlace(String id, PlaceRequest formPlace) {
        Place place = placeRepository.findByPlaceId(id)
            .orElseThrow(() -> new NotFoundException(ExceptionMessage.PLACE_NOT_FOUND));

        place.update(
            formPlace.getCity(),
            formPlace.getCountry(),
            formPlace.getPlaceName(),
            formPlace.getLatitude(),
            formPlace.getLongitude()
        );
    }

    @Transactional
    public void deleteById(String id) {
        Place place = placeRepository.findByPlaceId(id)
            .orElseThrow(() -> new NotFoundException(ExceptionMessage.PLACE_NOT_FOUND));
        placeRepository.delete(place);
    }

}
