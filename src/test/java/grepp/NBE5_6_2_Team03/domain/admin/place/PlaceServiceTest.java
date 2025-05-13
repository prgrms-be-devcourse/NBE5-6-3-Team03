package grepp.NBE5_6_2_Team03.domain.admin.place;

import grepp.NBE5_6_2_Team03.domain.admin.place.entity.Place;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PlaceServiceTest {

    @Autowired
    private GooglePlaceService placeService;

    @Test
    @DisplayName(value = "")
    void getPlaceIds() throws IOException {
        List<String> attraction = placeService.searchPlaceIds(34.6937,135.5023, 7000, "tourist_attraction", 1);
        List<String> restaurant = placeService.searchPlaceIds(34.6937,135.5023, 7000, "restaurant", 1);
        System.out.println("attraction : "+attraction.toString());
        System.out.println("restaurant : "+restaurant.toString());
    }

    @Test
    void getDetailsByPlaceIdTest() throws IOException {
        Place results = placeService.getDetailsByPlaceId("ChIJI_--Eg3nAGARcvVi6kRKyKM");
        System.out.println(results);
    }

//    @Test
//    void getDetailsByPlaceIds() throws IOException {
//        List<String> results = placeService.searchPlaceIds(34.6937,135.5023, 7000, "tourist_attraction", 1);
//        Map<String, Place> detailsByPlaceIds = placeService.getDetailsByPlaceIds(results);
//        System.out.println("wyatt: " + detailsByPlaceIds);
//    }

    @Test
    void getDetailsByPlaceIds() throws IOException {
        List<String> results = placeService.searchPlaceIds(34.6937, 135.5023, 7000, "tourist_attraction", 1);
        Set<String> placeIdSet = new HashSet<>(results);
        Map<String, Place> detailsByPlaceIds = placeService.getDetailsByPlaceIds(placeIdSet);
        System.out.println("wyatt: " + detailsByPlaceIds);
    }
}

