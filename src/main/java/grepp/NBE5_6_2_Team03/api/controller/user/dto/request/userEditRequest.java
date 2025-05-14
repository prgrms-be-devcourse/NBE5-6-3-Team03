package grepp.NBE5_6_2_Team03.api.controller.user.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class userEditRequest {

    private String email;
    private String rawPassword;
    private String name;
    private String phoneNumber;
    private MultipartFile profileImage;
}
