package grepp.NBE5_6_2_Team03.domain.admin.userinfo;

import grepp.NBE5_6_2_Team03.api.controller.admin.userinfo.dto.UserInfoResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.userinfo.dto.UserInfoUpdateRequest;
import grepp.NBE5_6_2_Team03.domain.admin.userinfo.repository.UserInfoRepository;
import grepp.NBE5_6_2_Team03.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;

    public Page<UserInfoResponse> findAll(Pageable pageable) {
        Page<UserInfo> userInfos = userInfoRepository.findAll(pageable);
        return userInfos.map(this::convertToResponse);
    }

    public UserInfoResponse convertToResponse(UserInfo userInfo) {
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
        UserInfo userInfo = userInfoRepository.findById(id).orElseThrow(
            () -> new NotFoundException("회원을 찾지 못했습니다.")
        );

        userInfo.update(
            request.getEmail(),
            request.getName(),
            request.getPhoneNumber()
        );

    }

    @Transactional
    public void deleteById(Long id) {
        UserInfo userInfo = userInfoRepository.findById(id).orElseThrow(
            () -> new NotFoundException("회원을 찾지 못했습니다.")
        );
        userInfoRepository.deleteById(id);
    }




}
