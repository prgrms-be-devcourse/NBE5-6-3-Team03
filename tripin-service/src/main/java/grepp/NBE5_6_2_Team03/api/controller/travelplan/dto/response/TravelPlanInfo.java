package grepp.NBE5_6_2_Team03.api.controller.travelplan.dto.response;

import grepp.NBE5_6_2_Team03.domain.travelplan.TravelPlan;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TravelPlanInfo {
 
    private final Long travelPlanId;
    private final String country;
    private final String name;
    private final int publicMoney;
    private final int applicants;
    private final LocalDate travelStartDate;
    private final LocalDate travelEndDate;

    @Builder
    private TravelPlanInfo(Long travelPlanId, String country, String name,
                           int publicMoney, int applicants, LocalDate travelStartDate,
                           LocalDate travelEndDate) {
        this.travelPlanId = travelPlanId;
        this.country = country;
        this.name = name;
        this.publicMoney = publicMoney;
        this.applicants = applicants;
        this.travelStartDate = travelStartDate;
        this.travelEndDate = travelEndDate;
    }

    public static TravelPlanInfo of(TravelPlan plan){
        return TravelPlanInfo.builder()
                .travelPlanId(plan.getTravelPlanId())
                .country(plan.getCountry().getCountryName())
                .name(plan.getName())
                .publicMoney(plan.getPublicMoney())
                .applicants(plan.getApplicants())
                .travelStartDate(plan.getTravelStartDate())
                .travelEndDate(plan.getTravelEndDate())
                .build();
    }
}
