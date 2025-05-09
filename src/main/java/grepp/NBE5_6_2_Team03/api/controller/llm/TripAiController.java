package grepp.NBE5_6_2_Team03.api.controller.llm;

import dev.langchain4j.model.chat.ChatLanguageModel;
import grepp.NBE5_6_2_Team03.api.controller.llm.dto.PlaceRecommendRequest;
import grepp.NBE5_6_2_Team03.api.controller.llm.dto.PlaceRecommendResponse;
import grepp.NBE5_6_2_Team03.api.controller.llm.service.TripAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class TripAiController {

    private final ChatLanguageModel chatLanguageModel;
    private final TripAiService tripAiService;



    @GetMapping("/recommend")
    public ResponseEntity<PlaceRecommendResponse> recommend(@RequestParam String region){
        PlaceRecommendRequest request = new PlaceRecommendRequest(region);
        PlaceRecommendResponse response = tripAiService.recommend(request);
        return ResponseEntity.ok(response);
    }
}
