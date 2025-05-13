package grepp.NBE5_6_2_Team03.domain.admin.place;

import com.fasterxml.jackson.databind.JsonNode;
import grepp.NBE5_6_2_Team03.domain.admin.place.entity.Place;
import grepp.NBE5_6_2_Team03.domain.admin.place.repository.PlaceRepository;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GooglePlaceService {

    private final PlaceRepository placeRepository;

    @Value("${google.maps.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final List<String> fields = List.of("name", "geometry/location",
    "address_components", "formatted_address", "photos");

    /**
     * limit은 최대 3번까지 가능, next_page_token 이 있는 경우 한정
     */
    public List<String> searchPlaceIds(double lat, double lng, int radius, String type, int limit) {
        List<String> placeIds = new ArrayList<>();
        String nextPageToken = null;
        int attempt = 0;

        do {
            URI uri = UriComponentsBuilder
                .fromHttpUrl("https://maps.googleapis.com/maps/api/place/nearbysearch/json")
                .queryParam("location", lat + "," + lng)
                .queryParam("radius", radius)
                .queryParam("type", type)
                .queryParam("key", apiKey)
                .queryParamIfPresent("pagetoken", nextPageToken == null ? java.util.Optional.empty() : java.util.Optional.of(nextPageToken))
                .build()
                .encode()
                .toUri();

            JsonNode root = restTemplate.getForObject(uri, JsonNode.class);
            JsonNode results = root.path("results");

            extractPlaceId(results, placeIds);

            nextPageToken = root.path("next_page_token").asText(null);
            attempt++;

            // next_page_token 를 포함한 요청은 2-5초후 요청해야 INVALID Request 가 안뜸
            if (nextPageToken != null) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        } while (nextPageToken != null && attempt < limit);

        return placeIds;
    }

    /**
     * 구글 주변 검색 결과 중 placeId만 추출
     */
    private static void extractPlaceId(JsonNode results, List<String> placeIds) {
        if (results.isArray()) {
            for (JsonNode result : results) {
                String status = result.path("business_status").asText();
                if ("OPERATIONAL".equals(status)) {
                    String placeId = result.path("place_id").asText();
                    placeIds.add(placeId);
                }
            }
        }
    }

    public Map<String, Place> getDetailsByPlaceIds(Set<String> placeIds) {
        Map<String, Place> resultMap = new HashMap<>();

        for (String id : placeIds) {
            Place place = getDetailsByPlaceId(id);
            place.setPlaceId(id);
            resultMap.put(id, place);
        }

        return resultMap;
    }

    public Place getDetailsByPlaceId(String placeId) {

        String fieldsString = String.join(",", fields);
        URI uri = UriComponentsBuilder
            .fromHttpUrl("https://maps.googleapis.com/maps/api/place/details/json")
            .queryParam("place_id", placeId)
            .queryParam("language", "ko")
            .queryParam("key", apiKey)
            .build()
            .encode()
            .toUri();

        JsonNode root = restTemplate.getForObject(uri, JsonNode.class);
        JsonNode result = root.path("result");

        String name = result.path("name").asText();
        JsonNode geometry = result.path("geometry").path("location");
        double lat = geometry.path("lat").asDouble();
        double lng = geometry.path("lng").asDouble();
        String address = result.path("formatted_address").asText();
        String country = null;
        String city = null;

        for (JsonNode component : result.path("address_components")) {
            JsonNode types = component.path("types");
            for (JsonNode type : types) {
                String typeText = type.asText();
                if (typeText.equals("country")) {
                    country = component.path("long_name").asText();
                } else if (typeText.equals("administrative_area_level_1")) {
                    city = component.path("long_name").asText();
                }
            }
        }

        return new Place(country, city, name, address, lat, lng);
    }

    @Transactional
    public void saveAll(Collection<Place> values) {
        placeRepository.saveAll(values);
    }

    @Transactional
    public void save(Place place) {
        placeRepository.save(place);
    }

    public boolean isDataInitialized() {
        return placeRepository.count() > 0;
    }

}