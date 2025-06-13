package grepp.NBE5_6_2_Team03.api.controller.expense;

import grepp.NBE5_6_2_Team03.api.controller.expense.dto.request.ExpenseRequest;
import grepp.NBE5_6_2_Team03.domain.expense.Expense;
import grepp.NBE5_6_2_Team03.domain.expense.service.ExpenseService;
import grepp.NBE5_6_2_Team03.domain.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/plan/{travelPlanId}/schedule/{travelScheduleId}/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping
    public String list(@PathVariable("travelPlanId") Long travelPlanId,
                       @PathVariable("travelScheduleId") Long travelScheduleId,
                       @AuthenticationPrincipal CustomUserDetails customUser,
                       Model model) {
        Expense expense = expenseService.findByScheduleId(travelScheduleId).orElse(null);
        model.addAttribute("expense", expense);
        model.addAttribute("travelPlanId", travelPlanId);
        model.addAttribute("travelScheduleId", travelScheduleId);
        model.addAttribute("username", customUser.getUsername());
        return "expense/expense-list";
    }

    @GetMapping("/add")
    public String addForm(@PathVariable("travelPlanId") Long travelPlanId,
                          @PathVariable("travelScheduleId") Long travelScheduleId,
                          @AuthenticationPrincipal CustomUserDetails customUser,
                          Model model) {
        model.addAttribute("travelPlanId", travelPlanId);
        model.addAttribute("travelScheduleId", travelScheduleId);
        model.addAttribute("username", customUser.getUsername());
        model.addAttribute("request", new ExpenseRequest());
        return "expense/expense-form";
    }

    @PostMapping("/add")
    public String addExpense(@PathVariable("travelPlanId") Long travelPlanId,
                             @PathVariable("travelScheduleId") Long travelScheduleId,
                             @ModelAttribute ExpenseRequest request,
                             Model model) {
        try {
            expenseService.addExpense(travelScheduleId, request);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("request", request);
            model.addAttribute("travelPlanId", travelPlanId);
            model.addAttribute("travelScheduleId", travelScheduleId);
            return "expense/expense-form";
        }

        return "redirect:/plan/" + travelPlanId + "/schedule/" + travelScheduleId + "/expense";
    }

    @GetMapping("/{expenseId}/edit")
    public String editForm(@PathVariable("travelPlanId") Long travelPlanId,
                           @PathVariable("travelScheduleId") Long travelScheduleId,
                           @PathVariable("expenseId") Long expenseId,
                           @AuthenticationPrincipal CustomUserDetails customUser,
                           Model model) {
        Expense expense = expenseService.findById(expenseId);

        model.addAttribute("travelPlanId", travelPlanId);
        model.addAttribute("travelScheduleId", travelScheduleId);
        model.addAttribute("expenseId", expenseId);
        model.addAttribute("username", customUser.getUsername());
        model.addAttribute("request", ExpenseRequest.fromEntity(expense));
        return "expense/expense-form";
    }

    @PostMapping("/{expenseId}/edit")
    public String editExpense(@PathVariable("travelPlanId") Long travelPlanId,
                              @PathVariable("travelScheduleId") Long travelScheduleId,
                              @PathVariable("expenseId") Long expenseId,
                              @ModelAttribute ExpenseRequest request,
                              Model model) {
        try {
            expenseService.editExpense(expenseId, request);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("request", request);
            model.addAttribute("travelPlanId", travelPlanId);
            model.addAttribute("travelScheduleId", travelScheduleId);
            model.addAttribute("expenseId", expenseId);
            return "expense/expense-form";
        }
        return "redirect:/plan/" + travelPlanId + "/schedule/" + travelScheduleId + "/expense";
    }

    @PostMapping("/{expenseId}/delete")
    public String deleteExpense(@PathVariable("travelPlanId") Long travelPlanId,
                                @PathVariable("travelScheduleId") Long travelScheduleId,
                                @PathVariable("expenseId") Long expenseId) {
        expenseService.deleteExpense(expenseId);
        return "redirect:/plan/" + travelPlanId + "/schedule/" + travelScheduleId + "/expense";
    }
}
