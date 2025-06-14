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
@Entity
public class TravelPlan extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long travelPlanId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @OneToMany(mappedBy = "travelPlan", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TravelSchedule> travelSchedules = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Country country;
    private String name;

    @Enumerated(EnumType.STRING)
    private CurrentUnit currentUnit;
  
    private int publicMoney;
    private int applicants;

    private LocalDate travelStartDate;
    private LocalDate travelEndDate;

    protected TravelPlan() {}

    @Builder
    private TravelPlan(User user, Country country, String name, CurrentUnit currentUnit,
                       int publicMoney, int applicants, LocalDate travelStartDate, LocalDate travelEndDate) {
        this.user = user;
        this.country = country;
        this.name = name;
        this.currentUnit = currentUnit;
        this.publicMoney = publicMoney;
        this.applicants = applicants;
        this.travelStartDate = travelStartDate;
        this.travelEndDate = travelEndDate;
    }

    public void modify(String name, Country country, int applicants,
                       CurrentUnit currentUnit, int publicMoney,
                       LocalDate travelStartDate, LocalDate travelEndDate) {

        this.name = name;
        this.country = country;
        this.applicants = applicants;
        this.currentUnit = currentUnit;
        this.publicMoney = publicMoney;
        this.travelStartDate = travelStartDate;
        this.travelEndDate = travelEndDate;
    }

}