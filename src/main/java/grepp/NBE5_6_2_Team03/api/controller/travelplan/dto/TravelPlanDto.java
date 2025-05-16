package grepp.NBE5_6_2_Team03.api.controller.travelplan.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder @AllArgsConstructor
public class TravelPlanDto {

    private String country;
    private String name;
    private int publicMoney;
    private int count;

    private LocalDate travelStartDate;
    private LocalDate travelEndDate;

    public TravelPlanDto() {}
}