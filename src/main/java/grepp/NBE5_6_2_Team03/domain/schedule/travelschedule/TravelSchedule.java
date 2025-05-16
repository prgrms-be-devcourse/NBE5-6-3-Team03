package grepp.NBE5_6_2_Team03.domain.schedule;

import grepp.NBE5_6_2_Team03.domain.plan.entity.TravelPlan;
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
public class TravelSchedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long travelScheduleId;

    @ManyToOne
    @JoinColumn(name = "travelPlanId")
    private TravelPlan travelPlan;

    private String content;
    private String placeName;
    private String departure;
    private String destination;
    private String transportation;

    @Enumerated(EnumType.STRING)
    private ScheduleStatus scheduleStatus;

    private LocalDate travelScheduleDate;
    private LocalDateTime createdDateTime;
    private LocalDateTime modifiedDateTime;

    public void edit(String content, String placeName, String departure, String destination, String transportation, LocalDate travelScheduleDate) {
        this.content = content;
        this.placeName = placeName;
        this.departure = departure;
        this.transportation = transportation;
        this.destination = destination;
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
