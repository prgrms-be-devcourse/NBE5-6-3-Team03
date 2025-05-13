package grepp.NBE5_6_2_Team03.api.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import grepp.NBE5_6_2_Team03.api.controller.user.dto.request.UserSignUpRequest;
import grepp.NBE5_6_2_Team03.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

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
                        .content(objectMapper.writeValueAsString(request))
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