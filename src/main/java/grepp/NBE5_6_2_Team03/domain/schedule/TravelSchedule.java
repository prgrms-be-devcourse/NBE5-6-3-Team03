package grepp.NBE5_6_2_Team03.domain.schedule;

import grepp.NBE5_6_2_Team03.domain.admin.place.entity.Place;
import grepp.NBE5_6_2_Team03.domain.plan.TravelPlan;
import grepp.NBE5_6_2_Team03.domain.schedule.code.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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

    @ManyToOne
    @JoinColumn(name = "placeId")
    private Place place;

    private String content;
    private String placeName;

    @Enumerated(EnumType.STRING)
    private Status isFinished;

    private Boolean recommend;
    private LocalDate travelScheduleDate;
    private LocalDateTime createdDateTime;
    private LocalDateTime modifiedDateTime;
}
