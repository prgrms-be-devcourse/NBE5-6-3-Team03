package grepp.NBE5_6_2_Team03.api.controller.expense;

import grepp.NBE5_6_2_Team03.api.controller.expense.dto.request.ExpenseRequest;
import grepp.NBE5_6_2_Team03.api.controller.expense.dto.response.ExpenseResponse;
import grepp.NBE5_6_2_Team03.domain.expense.Expense;
import grepp.NBE5_6_2_Team03.domain.expense.service.ExpenseService;
import grepp.NBE5_6_2_Team03.domain.user.CustomUserDetails;
import grepp.NBE5_6_2_Team03.global.response.ApiResponse;
import grepp.NBE5_6_2_Team03.global.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/expense")
@RequiredArgsConstructor
@RestController
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping("/{expenseId}")
    public ApiResponse<ExpenseResponse> findExpense(@PathVariable("expenseId") Long expenseId,
                                                    @AuthenticationPrincipal CustomUserDetails customUser) {
        Expense expense = expenseService.findById(expenseId);
        return ApiResponse.success(ExpenseResponse.fromEntity(customUser.getUsername(), expense));
    }

    @PostMapping("/add")
    public ApiResponse<Object> addExpense(@RequestParam("travelScheduleId") Long travelScheduleId,
                                          @RequestBody ExpenseRequest request,
                                          @AuthenticationPrincipal CustomUserDetails customUser) {
        try {
            Expense expense = expenseService.addExpense(travelScheduleId, request);
            return ApiResponse.success(ExpenseResponse.fromEntity(customUser.getUsername(), expense));
        } catch (IllegalArgumentException e) {
            return ApiResponse.error(ResponseCode.BAD_REQUEST, Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{expenseId}/edit")
    public ApiResponse<Object> editExpense(@PathVariable("expenseId") Long expenseId,
                                           @RequestBody ExpenseRequest request,
                                           @AuthenticationPrincipal CustomUserDetails customUser) {
        try {
            Expense expense = expenseService.editExpense(expenseId, request);
            return ApiResponse.success(ExpenseResponse.fromEntity(customUser.getUsername(), expense));
        } catch (IllegalArgumentException e) {
            return ApiResponse.error(ResponseCode.BAD_REQUEST, Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{expenseId}/delete")
    public ApiResponse<Map<String, Object>> deleteExpense(@PathVariable("expenseId") Long expenseId) {
        expenseService.deleteExpense(expenseId);
        return ApiResponse.noContent();
    }
}
