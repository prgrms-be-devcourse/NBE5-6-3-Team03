package grepp.NBE5_6_2_Team03.api.controller.admin.dto.user;

import grepp.NBE5_6_2_Team03.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoResponse {

    private Long id;
    private String email;
    private String name;
    private String phoneNumber;
    private Role role;

}
