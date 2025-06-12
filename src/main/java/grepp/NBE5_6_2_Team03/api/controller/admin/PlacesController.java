package grepp.NBE5_6_2_Team03.api.controller.admin;

import grepp.NBE5_6_2_Team03.api.controller.admin.dto.place.PlaceRequest;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.place.PlaceResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.place.PlaceSearchRequest;
import grepp.NBE5_6_2_Team03.domain.place.PlaceService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequiredArgsConstructor
@RequestMapping("/place")
public class PlacesController {
    private final PlaceService placeService;

    // STUDY ModelAttribute 어노테이션 공부
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

    @PostMapping("/{id}/edit")
    public ApiResponse<Map<String, String>> editPlace(@PathVariable("id") String id, PlaceRequest place,
        RedirectAttributes redirectAttributes) {
        placeService.updatePlace(id, place);
        return ApiResponse.success(createSuccessMessage("성공적으로 변경되었습니다."));
    }

    @PostMapping("/{id}/delete")
    public ApiResponse<Map<String, String>>  deletePlace(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        placeService.deleteById(id);
        return ApiResponse.success(createSuccessMessage("성공적으로 삭제되었습니다."));
    }

    private Map<String, String> createSuccessMessage(String message) {
        return Map.of(
            "message", message,
            "redirect", "/place/info"
        );
    }

    private Map<String, Object> createPlaceInfoResponse(Object obj) {
        List<String> countries = placeService.getCountries();
        List<String> cities = placeService.getCities();

        Map<String, Object> body = new HashMap<>();

        if (obj instanceof List<?>) {
            body.put("places", obj);
        } else {
            body.put("place", obj);
        }

        body.put("countries", countries);
        body.put("cities", cities);

        return body;
    }

}