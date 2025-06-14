package grepp.NBE5_6_2_Team03.api.controller.admin.dto.user;

import grepp.NBE5_6_2_Team03.domain.user.Role;
import grepp.NBE5_6_2_Team03.global.validation.annotation.EmailCheck;
import grepp.NBE5_6_2_Team03.global.validation.annotation.NameCheck;
import grepp.NBE5_6_2_Team03.global.validation.annotation.PhoneNumberCheck;
import lombok.Getter;

@Getter
public class UserInfoUpdateRequest {

    private Long id;
    @EmailCheck
    private String email;
    @NameCheck
    private String name;
    @PhoneNumberCheck
    private String phoneNumber;

    private Role role;

}
