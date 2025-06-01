package grepp.NBE5_6_2_Team03.api.controller.admin;

import grepp.NBE5_6_2_Team03.global.exception.NotFoundException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = AdminController.class)
public class AdminControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundException(NotFoundException e) {
        log.warn("NotFoundException occurred: {}", e.getMessage());
        return ResponseEntity.status(403)
            .body(createErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception e) {
        log.error("Unexpected exception occurred", e);
        return ResponseEntity.status(400)
            .body(createErrorMessage("알 수 없는 이유로 취소되었습니다."));
    }

    private Map<String, String> createErrorMessage(String message) {
        return Map.of(
            "message", message,
            "redirect", "/admin/user-info"
        );
    }
}
