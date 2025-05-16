package grepp.NBE5_6_2_Team03.api.controller.schedule.dto.request;

import grepp.NBE5_6_2_Team03.domain.plan.entity.TravelPlan;
import grepp.NBE5_6_2_Team03.domain.schedule.TravelSchedule;
import grepp.NBE5_6_2_Team03.domain.schedule.code.ScheduleStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    public TravelSchedule toEntity(TravelPlan plan) {
        return TravelSchedule.builder()
            .travelPlan(plan)
            .content(this.content)
            .placeName(this.placeName)
            .scheduleStatus(ScheduleStatus.PLANNED)
            .travelScheduleDate(this.travelScheduleDate)
            .createdDateTime(LocalDateTime.now())
            .build();
    }

    public static TravelScheduleRequest fromEntity(TravelSchedule schedule) {
        return new TravelScheduleRequest(
            schedule.getContent(),
            schedule.getPlaceName(),
            schedule.getTravelScheduleDate()
        );
    }
}
