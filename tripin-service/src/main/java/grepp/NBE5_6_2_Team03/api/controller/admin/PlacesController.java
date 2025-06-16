package grepp.NBE5_6_2_Team03.api.controller.admin;

import grepp.NBE5_6_2_Team03.api.controller.admin.dto.place.PlaceRequest;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.place.PlaceResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.place.PlaceSearchRequest;

import grepp.NBE5_6_2_Team03.domain.place.PlaceService;
import grepp.NBE5_6_2_Team03.global.message.AdminSuccessMessage;
import grepp.NBE5_6_2_Team03.global.response.ApiResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/place")
public class PlacesController {
    private final PlaceService placeService;

    @GetMapping("/info")
    public ApiResponse<Map<String, Object>> getPlaces(@ModelAttribute PlaceSearchRequest searchRequest ) {
        Page<PlaceResponse> places = placeService.findPlacesPageable(searchRequest);
        return ApiResponse.success(createPlaceInfoResponse(places));
    }

    @GetMapping("/{id}/edit")
    public ApiResponse<Map<String, Object>> editPlace(@PathVariable("id") String id) {
        PlaceResponse place = placeService.findById(id);
        return ApiResponse.success(createPlaceInfoResponse(place));
    }

    private Map<String, Object> createPlaceInfoResponse(Object obj) {
        List<String> countries = placeService.getCountries();
        List<String> cities = placeService.getCities();

        Map<String, Object> body = new HashMap<>();

        String placeKey = (obj instanceof List<?>) ? "places": "place";
        body.put(placeKey, obj);

        body.put("countries", countries);
        body.put("cities", cities);

        return body;
    }

    @PostMapping("/{id}/edit")
    public ApiResponse<AdminSuccessMessage> editPlace(@PathVariable("id") String id, PlaceRequest place) {
        placeService.updatePlace(id, place);
        return ApiResponse.success(AdminSuccessMessage.PLACE_UPDATED);
    }

    @PostMapping("/{id}/delete")
    public ApiResponse<Void>  deletePlace(@PathVariable("id") String id) {
        placeService.deleteById(id);
        return ApiResponse.noContent();
    }

}