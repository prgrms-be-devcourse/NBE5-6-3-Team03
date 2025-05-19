package grepp.NBE5_6_2_Team03.domain.expense;

import grepp.NBE5_6_2_Team03.domain.BaseEntity;
import grepp.NBE5_6_2_Team03.domain.travelschedule.TravelSchedule;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity
public class Expense extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expenseId;

    @OneToOne
    @JoinColumn(name = "travelScheduleId")
    private TravelSchedule travelSchedule;

    private int expectPrice;
    private int payedPrice;
    private String currency;
    private boolean isCompleted;
    private LocalDate expenseDate;

    protected Expense() {}

    @Builder
    private Expense(TravelSchedule travelSchedule, Integer expectPrice, Integer payedPrice,
                    String currency, boolean isCompleted, LocalDate expenseDate) {
        this.travelSchedule = travelSchedule;
        this.expectPrice = expectPrice;
        this.payedPrice = payedPrice;
        this.currency = currency;
        this.isCompleted = isCompleted;
        this.expenseDate = expenseDate;
        travelSchedule.setExpense(this);
    }

    public void edit(Integer expectPrice, Integer payedPrice) {
        this.expectPrice = expectPrice;
        this.payedPrice = payedPrice;
        this.isCompleted = (payedPrice != null);
    }
}
