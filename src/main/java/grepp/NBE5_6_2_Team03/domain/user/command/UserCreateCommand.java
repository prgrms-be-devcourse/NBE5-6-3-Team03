package grepp.NBE5_6_2_Team03.domain.user.command;

import grepp.NBE5_6_2_Team03.api.controller.user.dto.request.UserSignUpRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserCreateCommand {

    private String email;
    private String password;
    private String name;
    private String phoneNumber;

    @Builder
    private UserCreateCommand(String email, String password, String name, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public static UserCreateCommand from(UserSignUpRequest request) {
        return UserCreateCommand.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(request.getPassword())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }
}
