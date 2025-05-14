package grepp.NBE5_6_2_Team03.api.controller.user;

import grepp.NBE5_6_2_Team03.api.controller.user.dto.request.UserSignUpRequest;
import grepp.NBE5_6_2_Team03.domain.user.file.FileStore;
import grepp.NBE5_6_2_Team03.domain.user.service.UserService;
import grepp.NBE5_6_2_Team03.global.config.security.SecurityContextUpdater;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@WithMockUser
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FileStore fileStore;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private SecurityContextUpdater updater;

    @DisplayName("회원 가입 폼을 요청할 때 회원 가입 폼으로 이동 한다.")
    @Test
    void signUpForm() throws Exception {
        //given && when && then
        mockMvc.perform(
                get("/users/sign-up")
                        .with(csrf())
        )
                .andExpect(status().isOk())
                .andExpect(view().name("/user/signup-form"));
    }

    @DisplayName("회원 가입을 요청 할때 성공 한다면 메인 화면으로 리다이랙트 한다.")
    @Test
    void signUp() throws Exception {
        //given
        UserSignUpRequest request = createSignUpRequest("test@naver.com", "테스터");

        mockMvc.perform(
                post("/users/sign-up")
                        .param("email", request.getEmail())
                        .param("password", request.getPassword())
                        .param("name", request.getName())
                        .param("phoneNumber", request.getPhoneNumber())
                        .with(csrf())
        )
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrl("/"));
    }

    private UserSignUpRequest createSignUpRequest(String email, String name){
        return UserSignUpRequest.builder()
                .email(email)
                .name(name)
                .password("tempPassword!@")
                .phoneNumber("010-1234-5678")
                .build();
    }
}