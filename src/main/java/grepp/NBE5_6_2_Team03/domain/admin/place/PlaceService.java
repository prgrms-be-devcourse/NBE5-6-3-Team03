package grepp.NBE5_6_2_Team03.domain.admin.place;

import com.fasterxml.jackson.databind.JsonNode;
import grepp.NBE5_6_2_Team03.domain.admin.place.entity.Place;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class PlaceService {

    @Value("${google.maps.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
//    private final ObjectMapper objectMapper = new ObjectMapper();
    private final List<String> fields = List.of("name", "geometry/location",
    "address_components", "photos");

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

            if (results.isArray()) {
                for (JsonNode result : results) {
                    String status = result.path("business_status").asText();
                    if ("OPERATIONAL".equals(status)) {
                        String placeId = result.path("place_id").asText();
                        placeIds.add(placeId);
                    }
                }
            }

            nextPageToken = root.path("next_page_token").asText(null);
            attempt++;

            // next_page_Token 를 포함한 요청은 2-5초후 요청해야 INVALID TOKEN 이 안뜸
            if (nextPageToken != null) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        } while (nextPageToken != null && attempt < limit);

        return placeIds;
    }

    public Map<String, Place> getDetailsByPlaceIds(List<String> placeIds) {
        Map<String, Place> resultMap = new HashMap<>();

        for (String id : placeIds) {
            Place place = getDetailsByPlaceId(id);
            resultMap.put(id, place);
        }

        return resultMap;
    }

    public Place getDetailsByPlaceId(String placeId) {

        String fieldsString = String.join(",", fields);
        URI uri = UriComponentsBuilder
            .fromHttpUrl("https://maps.googleapis.com/maps/api/place/details/json")
            .queryParam("place_id", placeId)
            .queryParam("fields", fieldsString)
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
        String country = null;
        String administrativeAreaLevel1 = null;

        for (JsonNode component : result.path("address_components")) {
            JsonNode types = component.path("types");
            for (JsonNode type : types) {
                String typeText = type.asText();
                if (typeText.equals("country")) {
                    country = component.path("long_name").asText();
                } else if (typeText.equals("administrative_area_level_1")) {
                    administrativeAreaLevel1 = component.path("long_name").asText();
                }
            }
        }

        Place placeEntity = new Place();
        placeEntity.setPlaceName(name);
        placeEntity.setLatitude(lat);
        placeEntity.setLongitude(lng);
        placeEntity.setCountry(country);
        placeEntity.setCity(administrativeAreaLevel1);


        return placeEntity;
    }
}