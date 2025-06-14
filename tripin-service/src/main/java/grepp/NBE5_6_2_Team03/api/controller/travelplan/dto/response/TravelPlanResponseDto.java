package grepp.NBE5_6_2_Team03.api.controller.travelplan.dto.response;

import grepp.NBE5_6_2_Team03.domain.travelplan.TravelPlan;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class TravelPlanResponseDto {
 
    private final Long travelPlanId;
    private final String country;
    private final String name;
    private final int publicMoney;
    private final int count;
    private final LocalDate travelStartDate;
    private final LocalDate travelEndDate;

    public TravelPlanResponseDto(TravelPlan plan) {

        this.travelPlanId = plan.getTravelPlanId();
        this.country = plan.getCountry();
        this.name = plan.getName();
        this.publicMoney = plan.getPublicMoney();
        this.count = plan.getApplicants();
        this.travelStartDate = plan.getTravelStartDate();
        this.travelEndDate = plan.getTravelEndDate();
    }
}
