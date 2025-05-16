package grepp.NBE5_6_2_Team03.global.dataseed;

import grepp.NBE5_6_2_Team03.api.controller.admin.dto.place.CountryResponse;
import grepp.NBE5_6_2_Team03.domain.place.CountryService;
import grepp.NBE5_6_2_Team03.domain.place.GooglePlaceService;
import grepp.NBE5_6_2_Team03.domain.place.entity.Place;
import grepp.NBE5_6_2_Team03.global.dataseed.util.TranslationService;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlaceDataSeeder implements CommandLineRunner {

    private final GooglePlaceService googlePlaceService;
    private final CountryService countryService;
    private final TranslationService translationService;


    @Value("${app.data-initialized}")
    private boolean dataInitialized;

    @Override
    public void run(String[] args) {

        boolean isDataInitialized = googlePlaceService.isDataInitialized();
        if (isDataInitialized) {
            return;
        }

        List<CountryResponse> cities = countryService.getAllCities(); // 모든 도시 조회

        for (CountryResponse cityResponse : cities) {
            Set<String> placeIds = new HashSet<>();
            placeIds.addAll(googlePlaceService.searchPlaceIds(cityResponse.getLatitude(), cityResponse.getLongitude(), cityResponse.getRadius(), "tourist_attraction", 3));
            placeIds.addAll(googlePlaceService.searchPlaceIds(cityResponse.getLatitude(), cityResponse.getLongitude(), cityResponse.getRadius(), "point_of_interest", 3));

            Map<String, Place> details = googlePlaceService.getDetailsByPlaceIds(placeIds);

            // Place 객체에 placeId 설정 후 DB에 저장
            for (Place place : details.values()) {
                // 번역된 도시 이름 설정
                String translatedCityName = translationService.getTranslatedName(place.getCity());
                place.setPlaceId(place.getPlaceId());
                place.setCity(translatedCityName);
                String placeName = place.getPlaceName();
                String city = place.getCity();
                if (city != null && placeName != null && !placeName.isEmpty() && placeName.substring(0, 1).matches("[가-힣]")) {
                    googlePlaceService.save(place);
                }
            }
        }
    }

}
