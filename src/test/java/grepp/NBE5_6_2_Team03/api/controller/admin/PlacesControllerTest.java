package grepp.NBE5_6_2_Team03.api.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.place.PlaceSearchRequest;
import grepp.NBE5_6_2_Team03.global.response.ApiResponse;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PlacesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlacesController placesController; // You might not even need this if you're only testing via mockMvc

    @Test
    @DisplayName("여행지 페이지 페이지네이션 메서드 동작 테스트")
    void getPlaceInfoPage() throws JsonProcessingException, JsonProcessingException {
        for (int i = 0; i < 3; i++){
            PlaceSearchRequest searchRequest = new PlaceSearchRequest("일본", "도쿄", i, 3);
            ApiResponse<Map<String, Object>> result = placesController.getPlaces(searchRequest);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResult = objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(result);
            System.out.println(jsonResult);
        }
    }

    @Test
    @DisplayName("여행지 페이지 페이지네이션 WebMvc 테스트")
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