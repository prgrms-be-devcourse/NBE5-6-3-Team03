package grepp.NBE5_6_2_Team03.api.controller.admin.place;

import grepp.NBE5_6_2_Team03.api.controller.admin.place.dto.CityDto;
import grepp.NBE5_6_2_Team03.domain.admin.place.CityService;
import grepp.NBE5_6_2_Team03.domain.admin.place.GooglePlaceService;
import grepp.NBE5_6_2_Team03.domain.admin.place.entity.Place;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/place")
public class GooglePlacesController {

    private final String[] types = {"tourist_attraction", "point_of_interest"};
    private final GooglePlaceService googlePlaceService;
    private final CityService cityService;

    @PostMapping("/update")
    public ResponseEntity<Map<String, Place>> getPlaceIds(String city) {
        CityDto cityDto = cityService.toDto(city);
        Set<String> placeIds = new HashSet<>();

        placeIds.addAll( googlePlaceService.searchPlaceIds( // attraction 얻기
            cityDto.getLatitude(),
            cityDto.getLongitude(),
            cityDto.getRadius(),
            types[0],
            2));
        placeIds.addAll( googlePlaceService.searchPlaceIds( // attraction 얻기
            cityDto.getLatitude(),
            cityDto.getLongitude(),
            cityDto.getRadius(),
            types[1],
            2));

        Map<String, Place> details = googlePlaceService.getDetailsByPlaceIds(placeIds);

        return ResponseEntity.ok(details);
    }


}
