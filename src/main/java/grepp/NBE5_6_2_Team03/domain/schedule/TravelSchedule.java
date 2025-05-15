package grepp.NBE5_6_2_Team03.domain.schedule;

import grepp.NBE5_6_2_Team03.domain.plan.TravelPlan;
import grepp.NBE5_6_2_Team03.domain.schedule.code.ScheduleStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TravelSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long travelScheduleId;

    @ManyToOne
    @JoinColumn(name = "travelPlanId")
    private TravelPlan travelPlan;

    private String content;
    private String placeName;

    @Enumerated(EnumType.STRING)
    private ScheduleStatus scheduleStatus;

    private LocalDate travelScheduleDate;
    private LocalDateTime createdDateTime;
    private LocalDateTime modifiedDateTime;

    public void edit(String content, String placeName, LocalDate travelScheduleDate) {
        this.content = content;
        this.placeName = placeName;
        this.travelScheduleDate = travelScheduleDate;
        this.modifiedDateTime = LocalDateTime.now();
    }

    public boolean isCompleted() {
        return this.scheduleStatus == ScheduleStatus.COMPLETED;
    }

    public void toggleStatus() {
        if (isCompleted()) {
            this.scheduleStatus = ScheduleStatus.PLANNED;
        } else {
            this.scheduleStatus = ScheduleStatus.COMPLETED;
        }
        this.modifiedDateTime = LocalDateTime.now();
    }
}
