package grepp.NBE5_6_2_Team03.api.controller.travelplan.dto.request;

import grepp.NBE5_6_2_Team03.domain.travelplan.Country;
import grepp.NBE5_6_2_Team03.domain.travelplan.CurrentUnit;
import grepp.NBE5_6_2_Team03.domain.travelplan.TravelPlan;
import grepp.NBE5_6_2_Team03.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TravelPlanSaveRequest {

    private String name;
    private Country country;
    private CurrentUnit currentUnit;
    private int publicMoney;
    private int applicants;

    private LocalDate travelStartDate;
    private LocalDate travelEndDate;

    public TravelPlanSaveRequest() {}

    @Builder
    private TravelPlanSaveRequest(String name, Country country, CurrentUnit currentUnit,
                                  int publicMoney, int applicants, LocalDate travelStartDate,
                                  LocalDate travelEndDate) {
        this.name = name;
        this.country = country;
        this.currentUnit = currentUnit;
        this.publicMoney = publicMoney;
        this.applicants = applicants;
        this.travelStartDate = travelStartDate;
        this.travelEndDate = travelEndDate;
    }

    public TravelPlan toEntity(User user){
        return TravelPlan.builder()
                .user(user)
                .name(this.name)
                .country(this.country)
                .currentUnit(this.currentUnit)
                .publicMoney(this.publicMoney)
                .applicants(this.applicants)
                .travelStartDate(this.travelStartDate)
                .travelEndDate(this.travelEndDate)
                .build();
    }
}