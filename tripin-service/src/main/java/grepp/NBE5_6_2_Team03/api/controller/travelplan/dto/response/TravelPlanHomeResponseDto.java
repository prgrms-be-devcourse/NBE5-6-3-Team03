package grepp.NBE5_6_2_Team03.api.controller.travelplan.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TravelPlanHomeResponseDto {

    private String username;
    private List<TravelPlanResponseDto> plans;
}








