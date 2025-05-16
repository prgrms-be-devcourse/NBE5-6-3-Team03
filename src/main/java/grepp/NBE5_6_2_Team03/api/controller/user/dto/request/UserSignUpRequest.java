package grepp.NBE5_6_2_Team03.api.controller.user.dto.request;

import grepp.NBE5_6_2_Team03.global.validation.annotation.EmailCheck;
import grepp.NBE5_6_2_Team03.domain.user.User;
import grepp.NBE5_6_2_Team03.global.validation.annotation.NameCheck;
import grepp.NBE5_6_2_Team03.global.validation.annotation.PasswordCheck;
import grepp.NBE5_6_2_Team03.global.validation.annotation.PhoneNumberCheck;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static grepp.NBE5_6_2_Team03.domain.user.Role.*;

@Getter
@Setter
public class UserSignUpRequest {

    @EmailCheck
    private String email;

    @PasswordCheck
    private String password;

    @NameCheck
    private String name;

    @PhoneNumberCheck
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

    public User toEntity(String password) {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .phoneNumber(phoneNumber)
                .role(ROLE_USER)
                .build();
    }

}
