package grepp.NBE5_6_2_Team03.api.controller.admin.dto.user;

import grepp.NBE5_6_2_Team03.domain.user.Role;
import grepp.NBE5_6_2_Team03.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDetailResponse {

    private Long id;
    private String email;
    private String name;
    private String phoneNumber;
    private Boolean isLocked;
    private Role role;

    public static UserDetailResponse of(User user) {
        return new UserDetailResponse(
            user.getId(),
            user.getEmail(),
            user.getName(),
            user.getPhoneNumber(),
            user.isLocked(),
            user.getRole()
        );
    }

}
