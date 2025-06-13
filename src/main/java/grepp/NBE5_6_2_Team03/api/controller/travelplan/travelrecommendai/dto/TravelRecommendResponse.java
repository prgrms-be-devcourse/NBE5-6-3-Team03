package grepp.NBE5_6_2_Team03.api.controller.travelplan.travelrecommendai.dto;


import java.util.List;
import lombok.Data;

@Data
public class TravelRecommendResponse {
    private String message;
    private List<TravelRecommendation> recommendations;
}

