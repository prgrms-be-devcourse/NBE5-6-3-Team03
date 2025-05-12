package grepp.NBE5_6_2_Team03.api.controller.schedule.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class TravelScheduleRequest {

    @NotBlank
    private String content;
}
