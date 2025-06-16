package grepp.NBE5_6_2_Team03.api.controller.admin.dto.user;

import grepp.NBE5_6_2_Team03.domain.user.Role;
import grepp.NBE5_6_2_Team03.global.validation.annotation.EmailCheck;
import grepp.NBE5_6_2_Team03.global.validation.annotation.NameCheck;
import grepp.NBE5_6_2_Team03.global.validation.annotation.PhoneNumberCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoUpdateRequest {

    @EmailCheck
    private String email;
    @NameCheck
    private String name;
    @PhoneNumberCheck
    private String phoneNumber;

    private Role role;

}
