package grepp.NBE5_6_2_Team03.api.controller.user.dto.request;

import grepp.NBE5_6_2_Team03.domain.user.User;
import grepp.NBE5_6_2_Team03.global.validation.annotation.EmailCheck;
import grepp.NBE5_6_2_Team03.global.validation.annotation.NameCheck;
import grepp.NBE5_6_2_Team03.global.validation.annotation.PasswordCheck;
import grepp.NBE5_6_2_Team03.global.validation.annotation.PhoneNumberCheck;
import lombok.Builder;
import lombok.Getter;

import static grepp.NBE5_6_2_Team03.domain.user.Role.*;

@Getter
public class UserSignUpRequest {

    @EmailCheck
    private String email;

    @PasswordCheck
    private String password;

    @NameCheck
    private String name;

    @PhoneNumberCheck
    private String phone;

    public UserSignUpRequest() {
    }

    @Builder
    private UserSignUpRequest(String email, String password, String name, String phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
    }

    public User toEntity(String password) {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .phoneNumber(phone)
                .role(ROLE_USER)
                .build();
    }

}
