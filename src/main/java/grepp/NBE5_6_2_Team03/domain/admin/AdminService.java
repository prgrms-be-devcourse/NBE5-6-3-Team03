package grepp.NBE5_6_2_Team03.domain.admin;

import grepp.NBE5_6_2_Team03.api.controller.admin.dto.user.UserInfoResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.user.UserInfoUpdateRequest;
import grepp.NBE5_6_2_Team03.domain.user.User;
import grepp.NBE5_6_2_Team03.domain.user.repository.UserRepository;
import grepp.NBE5_6_2_Team03.global.exception.Message;
import grepp.NBE5_6_2_Team03.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final UserRepository userRepository;

    public Page<UserInfoResponse> findAll(Pageable pageable) {
        Page<User> userInfos = userRepository.findAll(pageable);
        return userInfos.map(this::convertToResponse);
    }

    public UserInfoResponse convertToResponse(User userInfo) {
        return new UserInfoResponse(
            userInfo.getId(),
            userInfo.getEmail(),
            userInfo.getName(),
            userInfo.getPhoneNumber(),
            userInfo.getRole()
        );
    }

    @Transactional
    public void updateUserInfo(Long id, UserInfoUpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new NotFoundException(Message.USER_NOT_FOUND)
        );

        user.updateProfile(
            request.getEmail(),
            request.getName(),
            request.getPhoneNumber(),
            null
        );

    }

    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

}
