package grepp.NBE5_6_2_Team03.domain.place;

import grepp.NBE5_6_2_Team03.api.controller.admin.dto.place.PlaceRequest;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.place.PlaceResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.place.PlaceSearchRequest;
import grepp.NBE5_6_2_Team03.domain.place.entity.Place;
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

    public List<PlaceResponse> findAll() {
        List<Place> places =  placeRepository.findAll();
        return places.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public Page<PlaceResponse> findPlacesPageable(PlaceSearchRequest req) {
        Page<Place> places = placeRepository.findPaged(req.getCountry(), req.getCity(), req.getPageable());
        log.debug("Found {} places", places.getTotalElements());
        return places.map(this::convertToDto);
    }

    public PlaceResponse findById(String id) {
        Place place = placeRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(ExceptionMessage.PLACE_NOT_FOUND));
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
