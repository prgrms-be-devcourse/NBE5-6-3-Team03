package grepp.NBE5_6_2_Team03.api.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.user.UserInfoResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.user.UserSearchRequest;
import grepp.NBE5_6_2_Team03.global.response.ApiResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminControllerTest {

    @Autowired
    private AdminController adminController;

    @Test
    void findUsersPage() {
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

}