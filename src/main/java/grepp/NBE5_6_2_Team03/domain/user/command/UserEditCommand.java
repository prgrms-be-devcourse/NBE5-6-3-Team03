package grepp.NBE5_6_2_Team03.domain.user.command;

import grepp.NBE5_6_2_Team03.api.controller.user.dto.request.userEditRequest;
import grepp.NBE5_6_2_Team03.domain.user.file.UploadFile;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserEditCommand {

    private String email;
    private String name;
    private String phoneNumber;
    private UploadFile uploadFile;

    @Builder
    private UserEditCommand(String email, String name, String phoneNumber, UploadFile uploadFile) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.uploadFile = uploadFile;
    }

    public static UserEditCommand of(userEditRequest request, UploadFile uploadFile) {
        return UserEditCommand.builder()
                .email(request.getEmail())
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .uploadFile(uploadFile)
                .build();
    }
}
