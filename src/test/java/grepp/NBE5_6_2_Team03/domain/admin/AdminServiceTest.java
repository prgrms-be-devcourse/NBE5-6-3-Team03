package grepp.NBE5_6_2_Team03.domain.admin;

import grepp.NBE5_6_2_Team03.api.controller.admin.dto.user.UserSearchRequest;
import grepp.NBE5_6_2_Team03.domain.user.User;
import grepp.NBE5_6_2_Team03.domain.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("유저 잠금 테스트")
    @Transactional
    void lockedUserTest() {
        Optional<User> user = userRepository.findById(3L);
        assertTrue(user.isPresent());
        adminService.lockUser(user.get().getId());
        assertTrue(user.get().isLocked());
    }

    @Test
    @DisplayName("유저 잠금해제 테스트")
    @Transactional
    void unlockedUserTest() {
        Optional<User> user = userRepository.findById(3L);
        assertTrue(user.isPresent());
        adminService.lockUser(user.get().getId());
        assertTrue(user.get().isLocked());
        adminService.unLockUser(user.get().getId());
        assertFalse(user.get().isLocked());
    }

}