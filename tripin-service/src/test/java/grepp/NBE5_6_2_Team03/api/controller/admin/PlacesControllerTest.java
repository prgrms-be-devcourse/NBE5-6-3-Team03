package grepp.NBE5_6_2_Team03.api.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.place.PlaceSearchRequest;
import grepp.NBE5_6_2_Team03.global.response.ApiResponse;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// FIXME : @WebMvcTest(PlaceController.class) 사용한 테스트로 변경
@SpringBootTest
@AutoConfigureMockMvc
class PlacesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlacesController placesController; // You might not even need this if you're only testing via mockMvc

    // FIXME : AssertJ 로 변경
    @Test
    @DisplayName("PlacesController.getPlaces() 메서드 동작 및 페이지네이션 결과 확인")
    void getPlaceInfoPage() throws JsonProcessingException {
        String testCountry = "일본";
        String testCity = "도쿄";
        int size = 3;
        int page = 3;

        for (int i = 0; i < page; i++){
            PlaceSearchRequest searchRequest = new PlaceSearchRequest(testCountry, testCity, i, size);
            ApiResponse<Map<String, Object>> apiResponse = placesController.getPlaces(searchRequest);
            ObjectMapper objectMapper = new ObjectMapper();

            String jsonResult = objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(apiResponse.data());
            System.out.println(jsonResult);

            Map<String, Object> responseData = objectMapper.readValue(jsonResult, Map.class);

            assertThat(responseData).isNotNull();

            Map<String, Object> placeData = (Map<String, Object>) responseData.get("place");
            assertThat(placeData).isNotNull();

            assertThat(placeData.get("numberOfElements")).isEqualTo(size);
            assertThat(placeData.get("size")).isEqualTo(size);
            assertThat(placeData.get("empty")).isEqualTo(false);

            List<Map<String, Object>> content = (List<Map<String, Object>>) placeData.get("content");
            assertThat(content).isNotNull().hasSize(size);

            for (Map<String, Object> place : content) {
                assertThat(place.get("placeId")).as("placeId should not be null").isNotNull();
                assertThat(place.get("country")).as("country should not be null").isNotNull();
                assertThat(place.get("city")).as("city should not be null").isNotNull();
                assertThat(place.get("placeName")).as("placeName should not be null").isNotNull();
                assertThat(place.get("latitude")).as("latitude should not be null").isNotNull();
                assertThat(place.get("longitude")).as("longitude should not be null").isNotNull();

                assertThat(place.get("country")).isEqualTo(testCountry);
                assertThat(place.get("city")).isEqualTo(testCity);
            }
        }
    }

    // FIXME : AssertJ 로 변경
    @Test
    @DisplayName("GET /place/info 엔드포인트 페이지네이션 WebMvc 테스트")
    @WithMockUser(username="admin", roles={"ADMIN"})
    void getPlaceInfoPage_webMvc() throws Exception {
        for (int i = 0; i < 3; i++) {
            PlaceSearchRequest searchRequest = new PlaceSearchRequest("일본", "도쿄", i, 3);

            mockMvc.perform(get("/place/info")
                    .param("country", searchRequest.getCountry())
                    .param("city", searchRequest.getCity())
                    .param("page", String.valueOf(searchRequest.getPage()))
                    .param("size", String.valueOf(searchRequest.getSize())))
                .andExpect(status().isOk())
                .andDo(print());
        }
    }
}