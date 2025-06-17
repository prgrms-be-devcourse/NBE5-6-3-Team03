package grepp.NBE5_6_2_Team03.domain.travelschedule;

import grepp.NBE5_6_2_Team03.domain.BaseEntity;
import grepp.NBE5_6_2_Team03.domain.travelplan.TravelPlan;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class TravelSchedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long travelScheduleId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "travel_plan_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TravelPlan travelPlan;

    @Embedded
    private TravelRoute travelRoute;

    private String content;
    private String placeName;

    @Enumerated(EnumType.STRING)
    private ScheduleStatus scheduleStatus;
    private LocalDateTime travelScheduleDate;
    private int expense;

    protected TravelSchedule() {}

    @Builder
    private TravelSchedule(TravelPlan travelPlan, TravelRoute travelRoute, String content, String placeName,
                           ScheduleStatus scheduleStatus, LocalDateTime travelScheduleDate, int expense) {
        this.travelPlan = travelPlan;
        this.travelRoute = travelRoute;
        this.content = content;
        this.placeName = placeName;
        this.scheduleStatus = scheduleStatus;
        this.travelScheduleDate = travelScheduleDate;
        this.expense = expense;
        travelPlan.getTravelSchedules().add(this);
    }

    public void edit(TravelRoute travelRoute, String content, String placeName, LocalDateTime travelScheduleDate, int expense) {
        this.travelRoute = travelRoute;
        this.content = content;
        this.placeName = placeName;
        this.travelScheduleDate = travelScheduleDate;
        this.expense = expense;
    }

    public void editStatus(ScheduleStatus status) {
        this.scheduleStatus = status;
    }

    public TravelRoute toResponse(TravelRoute travelRoute) {
        return new TravelRoute(travelRoute.getDeparture(), travelRoute.getDestination(), travelRoute.getTransportation(), null);
    }
}
