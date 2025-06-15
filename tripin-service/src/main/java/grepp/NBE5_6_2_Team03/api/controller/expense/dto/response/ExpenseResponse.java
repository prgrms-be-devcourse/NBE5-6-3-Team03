package grepp.NBE5_6_2_Team03.api.controller.expense.dto.response;

import grepp.NBE5_6_2_Team03.domain.expense.Expense;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ExpenseResponse {

    private final String username;
    private final Long expenseId;
    private final Long travelPlanId;
    private final Long travelScheduleId;
    private final int expectPrice;
    private final int payedPrice;
    private final boolean isCompleted;

    @Builder
    public ExpenseResponse(String username, Long expenseId, Long travelPlanId, Long travelScheduleId,
                           int expectPrice, int payedPrice, boolean isCompleted) {
        this.username = username;
        this.expenseId = expenseId;
        this.travelPlanId = travelPlanId;
        this.travelScheduleId = travelScheduleId;
        this.expectPrice = expectPrice;
        this.payedPrice = payedPrice;
        this.isCompleted = isCompleted;
    }

    public static ExpenseResponse fromEntity(String username, Expense expense) {
        return ExpenseResponse.builder()
            .username(username)
            .expenseId(expense.getExpenseId())
            .travelPlanId(expense.getTravelSchedule().getTravelPlan().getId())
            .travelScheduleId(expense.getTravelSchedule().getTravelScheduleId())
            .expectPrice(expense.getExpectPrice())
            .payedPrice(expense.getPayedPrice())
            .isCompleted(expense.isCompleted())
            .build();
    }
}
