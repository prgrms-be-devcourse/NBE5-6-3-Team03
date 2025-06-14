package grepp.NBE5_6_2_Team03.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    OK("200", HttpStatus.OK,"success"),
    BAD_REQUEST("4000", HttpStatus.BAD_REQUEST,"bad request"),
    UNAUTHORIZED("4001", HttpStatus.UNAUTHORIZED, "unAuthorized"),
    INVALID_TOKEN("4002", HttpStatus.UNAUTHORIZED, "invalid token"),
    FORBIDDEN("4030", HttpStatus.FORBIDDEN, "forbidden"),
    NOT_FOUND("4040", HttpStatus.NOT_FOUND, "not found"),
    INTERNAL_SERVER_ERROR("5000", HttpStatus.INTERNAL_SERVER_ERROR, "server error");

    private final String code;
    private final HttpStatus status;
    private final String message;

}
