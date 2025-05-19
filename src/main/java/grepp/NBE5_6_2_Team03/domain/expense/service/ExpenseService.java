package grepp.NBE5_6_2_Team03.domain.expense.service;

import grepp.NBE5_6_2_Team03.api.controller.expense.dto.request.ExpenseRequest;
import grepp.NBE5_6_2_Team03.domain.expense.Expense;
import grepp.NBE5_6_2_Team03.domain.expense.repository.ExpenseRepository;
import grepp.NBE5_6_2_Team03.domain.travelplan.TravelPlan;
import grepp.NBE5_6_2_Team03.domain.travelschedule.TravelSchedule;
import grepp.NBE5_6_2_Team03.domain.travelschedule.repository.TravelScheduleRepository;
import grepp.NBE5_6_2_Team03.global.exception.Message;
import grepp.NBE5_6_2_Team03.global.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final TravelScheduleRepository travelScheduleRepository;

    @Transactional
    public void addExpense(Long travelScheduleId, ExpenseRequest request) {
        TravelSchedule schedule = travelScheduleRepository.findById(travelScheduleId)
            .orElseThrow(() -> new NotFoundException(Message.SCHEDULE_NOT_FOUND));

        TravelPlan plan = schedule.getTravelPlan();

        if (getTotalPayedPrice(plan) + request.getPayedPrice() > plan.getPublicMoney()) {
            throw new IllegalArgumentException("공금이 부족합니다.");
        }

        Expense expense = request.toEntity(schedule);
        expenseRepository.save(expense);
    }

    @Transactional
    public void editExpense(Long expenseId, ExpenseRequest request) {
        Expense expense = expenseRepository.findById(expenseId)
            .orElseThrow(() -> new NotFoundException(Message.EXPENSE_NOT_FOUND));

        TravelPlan plan = expense.getTravelSchedule().getTravelPlan();

        int totalSum = getTotalPayedPrice(plan) - expense.getPayedPrice() + request.getPayedPrice();
        if (totalSum > plan.getPublicMoney()) {
            throw new IllegalArgumentException("공금이 부족합니다.");
        }

        expense.edit(
            request.getExpectPrice(),
            request.getPayedPrice()
        );
    }

    @Transactional
    public void deleteExpense(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
            .orElseThrow(() -> new NotFoundException(Message.EXPENSE_NOT_FOUND));

        TravelSchedule schedule = expense.getTravelSchedule();
        if (schedule != null) {
            schedule.deleteExpense();
        }

        expenseRepository.delete(expense);
    }

    public Expense findById(Long expenseId) {
        return expenseRepository.findById(expenseId)
            .orElseThrow(() -> new NotFoundException(Message.EXPENSE_NOT_FOUND));
    }

    public Optional<Expense> findByScheduleId(Long scheduleId) {
        TravelSchedule schedule = travelScheduleRepository.findById(scheduleId)
            .orElseThrow(() -> new NotFoundException(Message.SCHEDULE_NOT_FOUND));

        return expenseRepository.findByTravelSchedule(schedule);
    }

    private int getTotalPayedPrice(TravelPlan plan) {
        return plan.getTravelSchedules().stream()
            .map(TravelSchedule::getExpense)
            .filter(Objects::nonNull)
            .mapToInt(Expense::getPayedPrice)
            .sum();
    }
}
