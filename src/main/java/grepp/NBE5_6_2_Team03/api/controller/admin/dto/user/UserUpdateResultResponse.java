package grepp.NBE5_6_2_Team03.api.controller.admin.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserUpdateResultResponse {
    private final String message;
    private final String redirect;

}
