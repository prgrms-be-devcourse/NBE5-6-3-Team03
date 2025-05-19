package grepp.NBE5_6_2_Team03.api.controller.map;

import grepp.NBE5_6_2_Team03.api.controller.map.dto.MapResponse;
import grepp.NBE5_6_2_Team03.domain.map.MapService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MapController {

    private final MapService mapService;

    @GetMapping("/map")
    public String getMapPage(Model model,
        @RequestParam(name = "countryRequest", required = false) String countryRequest,
        @RequestParam(name = "cityRequest", required = false) String cityRequest,
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "20") int size) {

        int minPageLimit = Math.max(0, page);
        Pageable pageable = PageRequest.of(minPageLimit, size);
        Page<MapResponse> mapPage = mapService.findPlaces(countryRequest, cityRequest, pageable);

        List<String> countries = mapService.getCountries();
        List<String> cities;
        if (hasCityRequest(countryRequest, cityRequest)) {
            countryRequest = mapService.getCountryByCity(cityRequest).orElse(null);
            cities = mapService.getCity(countryRequest);
        } else if (hasCountryRequest(countryRequest)) {
            cities = mapService.getCity(countryRequest);
        } else {
            cities = mapService.getAllCities();
        }

        model.addAttribute("mapPage", mapPage);
        model.addAttribute("countries", countries);
        model.addAttribute("cities", cities);

        return "map/map";
    }

    private boolean hasCityRequest(String countryRequest, String cityRequest) {
        return cityRequest != null && (countryRequest == null || countryRequest.isEmpty());
    }

    private boolean hasCountryRequest(String countryRequest) {
        return countryRequest != null && !countryRequest.isEmpty();
    }

}
