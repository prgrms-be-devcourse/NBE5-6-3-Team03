package grepp.NBE5_6_2_Team03.api.controller.llm.dto;

import java.util.List;

public record PlaceRecommendResponse(List<PlaceDto> places,
                                     String reason) {

}
