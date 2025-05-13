package grepp.NBE5_6_2_Team03.api.controller.llm;

import dev.langchain4j.model.chat.ChatLanguageModel;
import grepp.NBE5_6_2_Team03.api.controller.llm.dto.PlaceRecommendResponse;
import grepp.NBE5_6_2_Team03.domain.llm.service.TripAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai")
public class TripAiController {

    private final ChatLanguageModel chatLanguageModel;
    private final TripAiService tripAiService;

    @PostMapping("/chat")
    public ResponseEntity<PlaceRecommendResponse> recommend(@RequestBody String message){
        PlaceRecommendResponse response = tripAiService.recommend(message);
        return ResponseEntity.ok(response);
    }
}