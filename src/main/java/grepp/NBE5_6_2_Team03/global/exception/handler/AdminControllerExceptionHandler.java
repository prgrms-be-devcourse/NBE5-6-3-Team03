package grepp.NBE5_6_2_Team03.global.exception.handler;

import grepp.NBE5_6_2_Team03.api.controller.admin.AdminController;
import grepp.NBE5_6_2_Team03.global.exception.CannotModifyAdminException;
import grepp.NBE5_6_2_Team03.global.exception.CannotUpdateException;
import grepp.NBE5_6_2_Team03.global.exception.NotFoundException;
import grepp.NBE5_6_2_Team03.global.response.ApiResponse;
import grepp.NBE5_6_2_Team03.global.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = AdminController.class)
public class AdminControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> handleNotFoundException(NotFoundException e) {
        log.warn("NotFoundException occurred: {}", e.getMessage());
        return ApiResponse.error(ResponseCode.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(CannotModifyAdminException.class)
    public ApiResponse<String> handleCannotModifyAdminException(CannotModifyAdminException e) {
        log.warn("CannotModifyAdminException occurred: {}", e.getMessage());
        return ApiResponse.error(ResponseCode.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(CannotUpdateException.class)
    public ApiResponse<String> handleCannotUpdateException(CannotModifyAdminException e) {
        log.warn("CannotUpdateException occurred: {}", e.getMessage());
        return ApiResponse.error(ResponseCode.FORBIDDEN, e.getMessage());
    }
}
