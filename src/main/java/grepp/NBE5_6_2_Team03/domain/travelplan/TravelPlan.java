package grepp.NBE5_6_2_Team03.domain.travelplan;

import grepp.NBE5_6_2_Team03.domain.BaseEntity;
import grepp.NBE5_6_2_Team03.domain.travelschedule.TravelSchedule;
import grepp.NBE5_6_2_Team03.domain.user.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.*;

@Getter
@Setter
@Table(name = "travel_plan")
@Entity
public class TravelPlan extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long travelPlanId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "travelPlan")
    private List<TravelSchedule> travelSchedules = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private CountryStatus countryStatus;

    private String country;
    private String name;
    private int publicMoney;
    private int count;

    private LocalDate travelStartDate;
    private LocalDate travelEndDate;

    protected TravelPlan() {}

    @Builder
    private TravelPlan(User user, CountryStatus countryStatus, String country, String name,
                      int publicMoney, int count, LocalDate travelStartDate, LocalDate travelEndDate) {
        this.user = user;
        this.countryStatus = countryStatus;
        this.country = country;
        this.name = name;
        this.publicMoney = publicMoney;
        this.count = count;
        this.travelStartDate = travelStartDate;
        this.travelEndDate = travelEndDate;
    }

}