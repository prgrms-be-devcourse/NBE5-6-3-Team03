package grepp.NBE5_6_2_Team03.global.exception.handler;

import grepp.NBE5_6_2_Team03.api.controller.admin.AdminController;
import grepp.NBE5_6_2_Team03.global.exception.NotFoundException;
import grepp.NBE5_6_2_Team03.global.response.ApiResponse;
import grepp.NBE5_6_2_Team03.global.response.ResponseCode;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = AdminController.class)
public class AdminControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<Map<String, String>> handleNotFoundException(NotFoundException e) {
        log.warn("NotFoundException occurred: {}", e.getMessage());
        return ApiResponse.error(ResponseCode.INTERNAL_SERVER_ERROR, createErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<Map<String, String>> handleGeneralException(Exception e) {
        log.error("Unexpected exception occurred", e);
        return ApiResponse.error(ResponseCode.BAD_REQUEST, createErrorMessage("알 수 없는 이유로 취소되었습니다."));
    }

    private Map<String, String> createErrorMessage(String message) {
        return Map.of(
            "message", message,
            "redirect", "/admin/user-info"
        );
    }
}
