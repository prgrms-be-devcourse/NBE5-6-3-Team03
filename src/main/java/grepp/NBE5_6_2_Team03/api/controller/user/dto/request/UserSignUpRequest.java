package grepp.NBE5_6_2_Team03.api.controller.user.dto.request;

import grepp.NBE5_6_2_Team03.domain.user.User;
import lombok.Builder;
import lombok.Setter;

import static grepp.NBE5_6_2_Team03.domain.user.Role.*;

@Setter
public class UserSignUpRequest {

    private String email;
    private String password;
    private String name;
    private String phoneNumber;

    public UserSignUpRequest() {
    }

    @Builder
    private UserSignUpRequest(String email, String password, String name, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public static UserSignUpRequest of(String email, String password, String name, String phoneNumber){
        return UserSignUpRequest.builder()
                .email(email)
                .password(password)
                .name(name)
                .phoneNumber(phoneNumber)
                .build();
    }

    public User toEntity(String password) {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .phoneNumber(phoneNumber)
                .role(ROLE_USER)
                .build();
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
