package grepp.NBE5_6_2_Team03.global.placeinitializer;

import grepp.NBE5_6_2_Team03.api.controller.admin.place.dto.CityDto;
import grepp.NBE5_6_2_Team03.domain.admin.place.CityService;
import grepp.NBE5_6_2_Team03.domain.admin.place.GooglePlaceService;
import grepp.NBE5_6_2_Team03.domain.admin.place.entity.Place;
import grepp.NBE5_6_2_Team03.domain.admin.place.util.TranslationService;
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
public class PlaceDataInitializer implements CommandLineRunner {

    private final GooglePlaceService googlePlaceService;
    private final CityService cityService;
    private final TranslationService translationService;

    @Value("${app.data-initialized}")
    private boolean dataInitialized;

    @Override
    public void run(String[] args) {
        // 이미 DB에 데이터가 있는지 확인
        boolean isDataInitialized = googlePlaceService.isDataInitialized();
        if (isDataInitialized) {
            return;
        }
        // 초기화되지 않았다면
        List<CityDto> cities = cityService.getAllCities(); // 모든 도시 조회

        for (CityDto cityDto : cities) {
            Set<String> placeIds = new HashSet<>();
            placeIds.addAll(googlePlaceService.searchPlaceIds(cityDto.getLatitude(), cityDto.getLongitude(), cityDto.getRadius(), "tourist_attraction", 3));
            placeIds.addAll(googlePlaceService.searchPlaceIds(cityDto.getLatitude(), cityDto.getLongitude(), cityDto.getRadius(), "point_of_interest", 3));

            Map<String, Place> details = googlePlaceService.getDetailsByPlaceIds(placeIds);

            // Place 객체에 placeId 설정 후 DB에 저장
            for (Place place : details.values()) {
                // 번역된 도시 이름 설정
//                String translatedCountryName = translationService.getTranslatedName(place.getCountry());
                String translatedCityName = translationService.getTranslatedName(place.getCity());
                place.setPlaceId(place.getPlaceId());
//                place.setCountry(translatedCountryName);
                place.setCity(translatedCityName);
                String placeName = place.getPlaceName();
                if (placeName != null && !placeName.isEmpty() && placeName.substring(0, 1).matches("[가-힣]")) {
                    googlePlaceService.save(place);
                }
            }
        }
    }

}
