package grepp.NBE5_6_2_Team03.api.controller.admin.dto.user;

import grepp.NBE5_6_2_Team03.global.validation.annotation.NameCheck;
import grepp.NBE5_6_2_Team03.global.validation.annotation.PhoneNumberCheck;
import lombok.Getter;

@Getter
public class UserModifyRequest {

    @NameCheck
    private String name;

    @PhoneNumberCheck
    private String phoneNumber;

    private Boolean isLocked;

    public UserModifyRequest() {}
}
