package grepp.NBE5_6_2_Team03.api.controller.plan.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelPlanDto {

    private String country;
    private String name;
    private int publicMoney;
    private int count;

    private LocalDate travelStartDate;
    private LocalDate travelEndDate;
}