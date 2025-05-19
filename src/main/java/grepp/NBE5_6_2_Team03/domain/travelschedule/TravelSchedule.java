package grepp.NBE5_6_2_Team03.domain.travelschedule;

import grepp.NBE5_6_2_Team03.domain.BaseEntity;
import grepp.NBE5_6_2_Team03.domain.expense.Expense;
import grepp.NBE5_6_2_Team03.domain.travelplan.TravelPlan;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Entity
public class TravelSchedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long travelScheduleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_plan_id")
    private TravelPlan travelPlan;

    @OneToOne(mappedBy = "travelSchedule", fetch = FetchType.LAZY)
    private Expense expense;

    @Embedded
    private TravelRoute travelRoute;

    private String content;
    private String placeName;

    @Enumerated(EnumType.STRING)
    private ScheduleStatus scheduleStatus;
    private LocalDate travelScheduleDate;

    protected TravelSchedule() {}

    @Builder
    private TravelSchedule(TravelPlan travelPlan, TravelRoute travelRoute, String content,
                           String placeName, ScheduleStatus scheduleStatus, LocalDate travelScheduleDate) {
        this.travelPlan = travelPlan;
        this.travelRoute = travelRoute;
        this.content = content;
        this.placeName = placeName;
        this.scheduleStatus = scheduleStatus;
        this.travelScheduleDate = travelScheduleDate;
        travelPlan.getTravelSchedules().add(this);
    }

    public void edit(TravelRoute travelRoute, String content, String placeName, LocalDate travelScheduleDate) {
        this.travelRoute = travelRoute;
        this.content = content;
        this.placeName = placeName;
        this.travelScheduleDate = travelScheduleDate;
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
    }
}
