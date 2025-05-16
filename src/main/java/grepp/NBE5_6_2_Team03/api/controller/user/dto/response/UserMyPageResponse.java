package grepp.NBE5_6_2_Team03.api.controller.user.dto.response;

import grepp.NBE5_6_2_Team03.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserMyPageResponse {
    private Long id;
    private String email;
    private String name;
    private String phoneNumber;
    private String storeFileName;

    @Builder
    private UserMyPageResponse(Long id, String email, String name, String phoneNumber, String storeFileName) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.storeFileName = storeFileName;
    }

    public static UserMyPageResponse from(User user){
        String storeFileName = user.getUploadFile() != null ? user.getUploadFile().getStoreFileName() : null;

        return UserMyPageResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .storeFileName(storeFileName)
                .build();
    }
}
