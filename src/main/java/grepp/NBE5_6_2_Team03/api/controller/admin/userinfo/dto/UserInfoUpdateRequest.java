package grepp.NBE5_6_2_Team03.api.controller.admin.userinfo.dto;

import grepp.NBE5_6_2_Team03.domain.user.Role;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserInfoUpdateRequest {

    private Long id;
    private String email;
    private String name;
    private String phoneNumber;
    private Role role;

}
