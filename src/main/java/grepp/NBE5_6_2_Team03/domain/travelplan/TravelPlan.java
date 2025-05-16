package grepp.NBE5_6_2_Team03.domain.travelplan;

import grepp.NBE5_6_2_Team03.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "travel_plan")
@Builder @AllArgsConstructor
public class TravelPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long travelPlanId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    CountryStatus countryStatus;

    private String country;
    private String name;
    private int publicMoney;
    private int count;

    private LocalDate travelStartDate;
    private LocalDate travelEndDate;

    private LocalDateTime createdDateTime;
    private LocalDateTime modifyDateTime;

    public TravelPlan() {

    }
}