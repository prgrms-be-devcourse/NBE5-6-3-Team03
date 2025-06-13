package grepp.NBE5_6_2_Team03.api.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.user.UserInfoResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.user.UserSearchRequest;
import grepp.NBE5_6_2_Team03.global.response.ApiResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
class AdminControllerTest {

    @Autowired
    private AdminController adminController;

    @Test
    @Transactional(readOnly = true)
    @DisplayName("잠금상태의 회원만 조회")
    void findLockedUsersPage() {
        // given
        UserSearchRequest request = UserSearchRequest
            .builder()
            .locked(true)
            .page(0)
            .size(10)
            .build();

        // when
        ApiResponse<Page<UserInfoResponse>> result = adminController.userInfos(request);

        // then
        List<UserInfoResponse> content = result.data().getContent();

        assertThat(content)
            .hasSize(2)
            .extracting("email", "name", "locked")
            .containsExactlyInAnyOrder(
                tuple("user16@example.com", "회원16", true),
                tuple("user17@example.com", "회원17", true)
            );
    }

    @Test
    @Transactional(readOnly = true)
    @DisplayName("잠금상태가 아닌 회원만 조회")
    void findUnlockedUserPage() throws JsonProcessingException {
        // given
        UserSearchRequest request = UserSearchRequest
            .builder()
            .locked(false)
            .page(0)
            .size(5)
            .build();

        // when
        ApiResponse<Page<UserInfoResponse>> result = adminController.userInfos(request);

        // then
        List<UserInfoResponse> content = result.data().getContent();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResult = objectMapper.writerWithDefaultPrettyPrinter()
            .writeValueAsString(content);
        System.out.println(jsonResult);
    }

    @Test
    @Transactional
    @DisplayName("유저 잠금 처리")
    void lockedUsers() throws JsonProcessingException {
        ApiResponse<String> result = adminController.lockUser(17L);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResult = objectMapper.writerWithDefaultPrettyPrinter()
            .writeValueAsString(result);
        System.out.println(jsonResult);
    }

    @Test
    @Transactional
    @DisplayName("유저 잠금 해제")
    void unlockedUsers() throws JsonProcessingException {
        ApiResponse<String> result = adminController.unlockUser(17L);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResult = objectMapper.writerWithDefaultPrettyPrinter()
            .writeValueAsString(result);
        System.out.println(jsonResult);
    }

}