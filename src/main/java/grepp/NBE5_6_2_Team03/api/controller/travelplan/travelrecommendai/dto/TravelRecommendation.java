package grepp.NBE5_6_2_Team03.api.controller.travelplan.travelrecommendai.dto;

import java.util.List;
import lombok.Data;

@Data
public class TravelRecommendation {
    private String destination;
    private String reason;
    private String recommendedPeriod;
    private String recommendedBudget;
    private List<String> activities;
    private String tip;
}

