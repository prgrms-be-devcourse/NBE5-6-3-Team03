package grepp.NBE5_6_2_Team03.api.controller.schedule.dto.request;

import grepp.NBE5_6_2_Team03.domain.schedule.TravelSchedule;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TravelScheduleRequest {

    @NotBlank
    private String content;
    private String placeName;
    @NotBlank
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate travelScheduleDate;

    public static TravelScheduleRequest fromEntity(TravelSchedule schedule) {
        return new TravelScheduleRequest(
            schedule.getContent(),
            schedule.getPlaceName(),
            schedule.getTravelScheduleDate()
        );
    }
}
