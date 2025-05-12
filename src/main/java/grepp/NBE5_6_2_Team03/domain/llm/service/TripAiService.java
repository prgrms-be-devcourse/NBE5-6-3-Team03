package grepp.NBE5_6_2_Team03.domain.llm.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import grepp.NBE5_6_2_Team03.api.controller.llm.dto.PlaceRecommendRequest;
import grepp.NBE5_6_2_Team03.api.controller.llm.dto.PlaceRecommendResponse;

@AiService(
    wiringMode = AiServiceWiringMode.EXPLICIT,
    chatModel = "googleAiGeminiChatModel"
)
public interface TripAiService {

    @SystemMessage("너는 지역 기반으로 관광지나 식당같은 곳을 추천해주는 관광 전문가야. 요청된 지역을 바탕으로 추천장소 3곳을 간단한 설명과 함께 알려줘. ")
    PlaceRecommendResponse recommend(@UserMessage PlaceRecommendRequest request);
}