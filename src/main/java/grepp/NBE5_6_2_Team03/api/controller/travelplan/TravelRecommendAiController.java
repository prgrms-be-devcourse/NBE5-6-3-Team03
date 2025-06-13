package grepp.NBE5_6_2_Team03.api.controller.travelplan;

import grepp.NBE5_6_2_Team03.api.controller.travelplan.travelrecommendai.dto.TravelRecommendRequest;
import grepp.NBE5_6_2_Team03.api.controller.travelplan.travelrecommendai.dto.TravelRecommendResponse;
import grepp.NBE5_6_2_Team03.domain.travelplan.travelrecommendai.TravelRecommendAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TravelRecommendAiController {

    private final TravelRecommendAiService travelRecommendAiService;

    @GetMapping("/plan/recommend")
    public ResponseEntity<TravelRecommendResponse> recommend(@ModelAttribute TravelRecommendRequest travelRecommendRequest)
    {
        TravelRecommendResponse result = travelRecommendAiService.recommendplan(travelRecommendRequest);
        return ResponseEntity.ok(result);
    }

}
