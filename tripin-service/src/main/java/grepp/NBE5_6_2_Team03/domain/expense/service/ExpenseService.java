package grepp.NBE5_6_2_Team03.domain.expense.service;

import grepp.NBE5_6_2_Team03.api.controller.expense.dto.request.ExpenseRequest;
import grepp.NBE5_6_2_Team03.domain.expense.Expense;
import grepp.NBE5_6_2_Team03.domain.expense.repository.ExpenseRepository;
import grepp.NBE5_6_2_Team03.domain.travelplan.TravelPlan;
import grepp.NBE5_6_2_Team03.domain.travelschedule.TravelSchedule;
import grepp.NBE5_6_2_Team03.domain.travelschedule.repository.TravelScheduleRepository;
import grepp.NBE5_6_2_Team03.global.message.ExceptionMessage;
import grepp.NBE5_6_2_Team03.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final TravelScheduleRepository travelScheduleRepository;

    @Transactional
    public Expense addExpense(Long travelScheduleId, ExpenseRequest request) {
        TravelSchedule schedule = travelScheduleRepository.findById(travelScheduleId)
            .orElseThrow(() -> new NotFoundException(ExceptionMessage.SCHEDULE_NOT_FOUND));

        TravelPlan plan = schedule.getTravelPlan();
        validatePayedPrice(plan, request.getPayedPrice());

        Expense expense = request.toEntity(schedule);
        expenseRepository.save(expense);

        return expense;
    }

    @Transactional
    public Expense editExpense(Long expenseId, ExpenseRequest request) {
        Expense expense = expenseRepository.findById(expenseId)
            .orElseThrow(() -> new NotFoundException(ExceptionMessage.EXPENSE_NOT_FOUND));

        TravelPlan plan = expense.getTravelSchedule().getTravelPlan();
        validatePayedPriceForEdit(plan, expense.getPayedPrice(), request.getPayedPrice());

        expense.edit(
            request.getExpectPrice(),
            request.getPayedPrice()
        );

        return expense;
    }

    @Transactional
    public void deleteExpense(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
            .orElseThrow(() -> new NotFoundException(ExceptionMessage.EXPENSE_NOT_FOUND));

        TravelSchedule schedule = expense.getTravelSchedule();
        if (schedule != null) {
            schedule.deleteExpense();
        }

        expenseRepository.delete(expense);
    }

    public Expense findById(Long expenseId) {
        return expenseRepository.findById(expenseId)
            .orElseThrow(() -> new NotFoundException(ExceptionMessage.EXPENSE_NOT_FOUND));
    }

    private int getTotalPayedPrice(TravelPlan plan) {
        return expenseRepository.sumPayedPriceByPlanId(plan.getTravelPlanId());
    }

    private void validatePayedPrice(TravelPlan plan, int newPayedPrice) {
        if (getTotalPayedPrice(plan) + newPayedPrice > plan.getPublicMoney()) {
            throw new IllegalArgumentException("공금이 부족합니다.");
        }
    }

    private void validatePayedPriceForEdit(TravelPlan plan, int oldPayedPrice, int newPayedPrice) {
        int totalSum = getTotalPayedPrice(plan) - oldPayedPrice + newPayedPrice;
        if (totalSum > plan.getPublicMoney()) {
            throw new IllegalArgumentException("공금이 부족합니다.");
        }
    }
}
