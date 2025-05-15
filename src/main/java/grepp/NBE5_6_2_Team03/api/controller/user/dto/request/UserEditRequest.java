package grepp.NBE5_6_2_Team03.api.controller.user.dto.request;

import grepp.NBE5_6_2_Team03.global.validation.annotation.EmailCheck;
import grepp.NBE5_6_2_Team03.global.validation.annotation.NameCheck;
import grepp.NBE5_6_2_Team03.global.validation.annotation.PhoneNumberCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UserEditRequest {

    @EmailCheck
    private String email;

    @NotBlank(message = "비밀번호는 빈 값을 허용 하지 않습니다.")
    private String rawPassword;

    @NameCheck
    private String name;

    @PhoneNumberCheck
    private String phoneNumber;
    private MultipartFile profileImage;

    protected UserEditRequest() {}

    @Builder
    private UserEditRequest(String email, String rawPassword, String name, String phoneNumber, MultipartFile profileImage) {
        this.email = email;
        this.rawPassword = rawPassword;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
    }
}
