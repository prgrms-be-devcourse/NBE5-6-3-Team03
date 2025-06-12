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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/info")
    public ApiResponse<Map<String, Object>> getPlaces(PlaceSearchRequest searchRequest ) {
//        List<PlaceResponse> places = placeService.findAll();
        Page<PlaceResponse> result = placeService.searchPlaces(request);

        return ApiResponse.success(createPlaceInfoResponse(places));
    }

    @GetMapping("/{id}/edit")
    public ApiResponse<Map<String, Object>> editPlace(@PathVariable("id") String id) {
        PlaceResponse place = placeService.findById(id);
        return ApiResponse.success(createPlaceInfoResponse(place));
    }

    @PostMapping("/{id}/edit")
    public String editPlace(@PathVariable("id") String id, PlaceRequest place,
        RedirectAttributes redirectAttributes) {
        placeService.updatePlace(id, place);

        redirectAttributes.addFlashAttribute("message", "Place updated successfully.");
        return "redirect:/place/info";
    }

    @PostMapping("/{id}/delete")
    public String deletePlace(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        placeService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Place deleted successfully.");
        return "redirect:/place/info";
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