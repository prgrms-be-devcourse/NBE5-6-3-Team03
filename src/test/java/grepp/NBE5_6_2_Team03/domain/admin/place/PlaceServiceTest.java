package grepp.NBE5_6_2_Team03.domain.admin.place;

import grepp.NBE5_6_2_Team03.domain.admin.place.entity.Place;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PlaceServiceTest {

    @Autowired
    private PlaceService placeService;

    @Test
    void getPlaceIds() throws IOException {
        List<String> results = placeService.searchPlaceIds(34.6937,135.5023, 7000, "tourist_attraction", 1);
        System.out.println(results.size());
    }

    @Test
    void getDetailsByPlaceIdTest() throws IOException {
        Place results = placeService.getDetailsByPlaceId("ChIJ67mcWJLmAGARrUf0FlFtm7w");
        System.out.println(results);
    }

    @Test
    void getDetailsByPlaceIds() throws IOException {
        List<String> results = placeService.searchPlaceIds(34.6937,135.5023, 7000, "tourist_attraction", 1);
        Map<String, Place> detailsByPlaceIds = placeService.getDetailsByPlaceIds(results);
        System.out.println("wyatt: " + detailsByPlaceIds);
    }
}