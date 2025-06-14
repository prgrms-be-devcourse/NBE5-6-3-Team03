package grepp.NBE5_6_2_Team03.api.controller.travelplan.dto.response;

import java.util.List;

import lombok.Getter;

@Getter
public class TravelPlansResponse {

    private String username;
    private List<TravelPlanInfo> plans;

    public TravelPlansResponse(String username, List<TravelPlanInfo> plans) {
        this.username = username;
        this.plans = plans;
    }
}








