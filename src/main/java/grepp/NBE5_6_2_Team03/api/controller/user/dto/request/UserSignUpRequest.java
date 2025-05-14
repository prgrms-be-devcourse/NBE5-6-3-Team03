package grepp.NBE5_6_2_Team03.api.controller.user.dto.request;

import grepp.NBE5_6_2_Team03.domain.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static grepp.NBE5_6_2_Team03.domain.user.Role.*;

@Getter
@Setter
public class UserSignUpRequest {

    @Pattern(
            regexp = "^[A-Za-z0-9]+@[A-Za-z0-9]+\\.com$",
            message = "올바른 이메일 형식이어야 합니다."
    )
    @NotBlank(message = "이메일은 빈 값을 허용 하지 않습니다.")
    private String email;

    @Pattern(
            regexp = "^(?=(?:[^!@#$%^&*()]*[!@#$%^&*()]){2,})[A-Za-z0-9!@#$%^&*()]{8,15}$",
            message = "비밀번호는 최소 8자 ~ 15자이며 특수문자를 2개 이상 포함해야 합니다."
    )
    @NotBlank(message = "비밀번호는 빈 값을 허용 하지 않습니다.")
    private String password;

    @Pattern(
            regexp = "^[A-Za-z가-힣0-9]{2,10}$",
            message = "회원의 이름은 한글과 영어 문자만 가능하며 최대 10자리 입니다."
    )
    @NotBlank(message = "이름은 빈 값을 허용 하지 않습니다.")
    private String name;

    @Pattern(
            regexp = "^01[016789]-\\d{3,4}-\\d{4}$",
            message = "휴대폰 번호는 010-1234-5678 형식으로 입력해주세요."
    )
    @NotBlank(message = "휴대폰 번호는 빈 값을 허용 하지 않습니다.")
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

}
