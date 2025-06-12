package grepp.NBE5_6_2_Team03.domain.place;

import com.fasterxml.jackson.databind.JsonNode;
import grepp.NBE5_6_2_Team03.domain.place.entity.Place;
import grepp.NBE5_6_2_Team03.domain.place.repository.PlaceRepository;
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
    private static final int MAX_REQUEST = 3;
    private final PlaceRepository placeRepository;

    @Value("${google.maps.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final List<String> fields = List.of("name", "geometry/location",
    "address_components", "formatted_address", "photos");

    public List<String> searchPlaceIds(double lat, double lng, int radius, String type, int maxPageRequests
    ) {
        List<String> placeIds = new ArrayList<>();
        String nextPageToken = null;
        int attempt = 0;
        int limit = Math.min(maxPageRequests, MAX_REQUEST);

         do {
            URI uri = getDetailsUriWithNextPageToken(lat, lng, radius, type, nextPageToken);

            JsonNode root = restTemplate.getForObject(uri, JsonNode.class);
            JsonNode results = root.path("results");

            extractPlaceId(results, placeIds);

            nextPageToken = root.path("next_page_token").asText(null);
            attempt++;

            // next_page_token 를 포함한 요청은 2-5초후 요청해야 INVALID Request 가 안뜸
            if (nextPageToken != null) {
                waitUntilToValidToken();
            }
        } while ( hasNextPage(nextPageToken) && attempt < limit);

        return placeIds;
    }

    private boolean hasNextPage(String nextPageToken) {
        return nextPageToken != null;
    }

    private void waitUntilToValidToken() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
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
        URI uri = getDetailsUri(placeId);

        JsonNode root = restTemplate.getForObject(uri, JsonNode.class);
        JsonNode result = root.path("result");

        String name = result.path("name").asText();
        JsonNode geometry = result.path("geometry").path("location");
        double lat = geometry.path("lat").asDouble();
        double lng = geometry.path("lng").asDouble();
        String address = result.path("formatted_address").asText();
        String country = null;
        String city = null;

        // address_components 에서 원하는 속성만을 추출
        for (JsonNode component : result.path("address_components")) {
            List<String> typeList = new ArrayList<>();
            for (JsonNode typeNode : component.path("types")) {
                typeList.add(typeNode.asText());
            }

            if (typeList.contains("country")) {
                country = component.path("long_name").asText();
            }

            if (typeList.contains("administrative_area_level_1")) {
                city = component.path("long_name").asText();
            }
        }

        return new Place(country, city, name, lat, lng);
    }

    private URI getDetailsUri(String placeId) {
        URI uri = UriComponentsBuilder
            .fromHttpUrl("https://maps.googleapis.com/maps/api/place/details/json")
            .queryParam("place_id", placeId)
            .queryParam("language", "ko")
            .queryParam("key", apiKey)
            .build()
            .encode()
            .toUri();
        return uri;
    }

    private URI getDetailsUriWithNextPageToken(double lat, double lng, int radius, String type, String nextPageToken) {
        URI uri = UriComponentsBuilder
            .fromHttpUrl("https://maps.googleapis.com/maps/api/place/nearbysearch/json")
            .queryParam("location", lat + "," + lng)
            .queryParam("radius", radius)
            .queryParam("type", type)
            .queryParam("key", apiKey)
            .queryParamIfPresent("pagetoken", nextPageToken == null ? java.util.Optional.empty() : java.util.Optional.of(
                nextPageToken))
            .build()
            .encode()
            .toUri();
        return uri;
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