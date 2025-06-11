package grepp.NBE5_6_2_Team03.api.controller.expense;

import grepp.NBE5_6_2_Team03.api.controller.expense.dto.request.ExpenseRequest;
import grepp.NBE5_6_2_Team03.api.controller.expense.dto.response.ExpenseResponse;
import grepp.NBE5_6_2_Team03.domain.expense.Expense;
import grepp.NBE5_6_2_Team03.domain.expense.service.ExpenseService;
import grepp.NBE5_6_2_Team03.domain.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plan/{travelPlanId}/schedule/{travelScheduleId}/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> list(@PathVariable("travelPlanId") Long travelPlanId,
                                  @PathVariable("travelScheduleId") Long travelScheduleId,
                                  @AuthenticationPrincipal CustomUserDetails customUser) {
        Expense expense = expenseService.findByScheduleId(travelScheduleId).orElse(null);

        Map<String, Object> response = new HashMap<>();
        response.put("expense", expense != null ? ExpenseResponse.fromEntity(expense) : null);
        response.put("travelPlanId", travelPlanId);
        response.put("travelScheduleId", travelScheduleId);
        response.put("username", customUser.getUsername());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addExpense(@PathVariable("travelPlanId") Long travelPlanId,
                             @PathVariable("travelScheduleId") Long travelScheduleId,
                             @RequestBody ExpenseRequest request) {
        try {
            Expense expense = expenseService.addExpense(travelScheduleId, request);
            return ResponseEntity.ok(ExpenseResponse.fromEntity(expense));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{expenseId}/edit")
    public ResponseEntity<Object> editExpense(@PathVariable("travelPlanId") Long travelPlanId,
                              @PathVariable("travelScheduleId") Long travelScheduleId,
                              @PathVariable("expenseId") Long expenseId,
                              @RequestBody ExpenseRequest request) {
        try {
            Expense expense = expenseService.editExpense(expenseId, request);
            return ResponseEntity.ok(ExpenseResponse.fromEntity(expense));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{expenseId}/delete")
    public ResponseEntity<Map<String, Object>> deleteExpense(@PathVariable("travelPlanId") Long travelPlanId,
                                @PathVariable("travelScheduleId") Long travelScheduleId,
                                @PathVariable("expenseId") Long expenseId) {
        expenseService.deleteExpense(expenseId);
        return ResponseEntity.noContent().build();
    }
}
