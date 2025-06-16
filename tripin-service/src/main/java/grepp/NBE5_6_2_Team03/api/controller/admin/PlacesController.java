package grepp.NBE5_6_2_Team03.api.controller.admin;

import grepp.NBE5_6_2_Team03.api.controller.admin.dto.place.request.PlaceRequest;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.place.request.PlaceSearchRequest;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.place.response.CountryCityInfo;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.place.response.PlaceDetailResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.place.response.PlaceInfoListResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.place.response.PlaceInfoResponse;
import grepp.NBE5_6_2_Team03.domain.place.PlaceService;
import grepp.NBE5_6_2_Team03.global.message.AdminSuccessMessage;
import grepp.NBE5_6_2_Team03.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class PlacesController {
    private final PlaceService placeService;

    @PostMapping("/places")
    public ApiResponse<PlaceInfoListResponse> getPlaces(@RequestBody PlaceSearchRequest searchRequest ) {
        Page<PlaceInfoResponse> places = placeService.findPlacesPage(searchRequest);
        CountryCityInfo countryCityInfo = placeService.getCountryCityInfo();
        PlaceInfoListResponse response = PlaceInfoListResponse.of(
                                            places,
                                            countryCityInfo.getCountries(),
                                            countryCityInfo.getCities()
                                        );
        return ApiResponse.success(response);
    }

    @GetMapping("/places/{id}")
    public ApiResponse<PlaceDetailResponse> getPlaceById(@PathVariable("id") String id) {
        PlaceInfoResponse place = placeService.findById(id);
        CountryCityInfo countryCityInfo = placeService.getCountryCityInfo();
        PlaceDetailResponse response = PlaceDetailResponse.of(
                                            place,
                                            countryCityInfo.getCountries(),
                                            countryCityInfo.getCities()
                                        );
        return ApiResponse.success(response);
    }

    @PatchMapping("/places/{id}")
    public ApiResponse<AdminSuccessMessage> updatePlace(
        @PathVariable("id") String id,
        @RequestBody PlaceRequest place
    ) {
        placeService.updatePlace(id, place);
        return ApiResponse.success(AdminSuccessMessage.PLACE_UPDATED);
    }

    @DeleteMapping("/places/{id}")
    public ApiResponse<Void>  deletePlace(@PathVariable("id") String id) {
        placeService.deleteById(id);
        return ApiResponse.noContent();
    }

}