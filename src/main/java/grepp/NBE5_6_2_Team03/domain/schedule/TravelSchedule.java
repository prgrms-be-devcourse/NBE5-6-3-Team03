package grepp.NBE5_6_2_Team03.domain.schedule;

import grepp.NBE5_6_2_Team03.domain.plan.TravelPlan;
import grepp.NBE5_6_2_Team03.domain.schedule.code.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
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
    private LocalDateTime createdDateTime;
    private LocalDateTime modifiedDateTime;

    @Enumerated(EnumType.STRING)
    private Status isFinished;
}
