package grepp.NBE5_6_2_Team03.api.controller.expense.dto.response;

import grepp.NBE5_6_2_Team03.domain.expense.Expense;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ExpenseResponse {

    private final Long expenseId;
    private final int expectPrice;
    private final int payedPrice;
    private final boolean isCompleted;

    @Builder
    public ExpenseResponse(Long expenseId, int expectPrice, int payedPrice, boolean isCompleted) {
        this.expenseId = expenseId;
        this.expectPrice = expectPrice;
        this.payedPrice = payedPrice;
        this.isCompleted = isCompleted;
    }

    public static ExpenseResponse fromEntity(Expense expense) {
        return new ExpenseResponse(
            expense.getExpenseId(),
            expense.getExpectPrice(),
            expense.getPayedPrice(),
            expense.isCompleted()
        );
    }
}
