package grepp.NBE5_6_2_Team03.global.exception.handler;

import grepp.NBE5_6_2_Team03.api.controller.admin.AdminController;
import grepp.NBE5_6_2_Team03.global.exception.CannotModifyAdminException;
import grepp.NBE5_6_2_Team03.global.exception.NotFoundException;
import grepp.NBE5_6_2_Team03.global.response.ApiResponse;
import grepp.NBE5_6_2_Team03.global.response.ResponseCode;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = AdminController.class)
public class AdminControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<Map<String, String>> handleNotFoundException(NotFoundException e) {
        log.warn("NotFoundException occurred: {}", e.getMessage());
        return ApiResponse.error(ResponseCode.NOT_FOUND, createErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(CannotModifyAdminException.class)
    public ApiResponse<Map<String, String>> handleCannotModifyAdminException(CannotModifyAdminException e) {
        log.warn("CannotModifyAdminException occurred: {}", e.getMessage());
        return ApiResponse.error(ResponseCode.FORBIDDEN, createErrorMessage(e.getMessage()));
    }

    private Map<String, String> createErrorMessage(String message) {
        return Map.of(
            "message", message,
            "redirect", "/admin/user-info"
        );
    }
}
