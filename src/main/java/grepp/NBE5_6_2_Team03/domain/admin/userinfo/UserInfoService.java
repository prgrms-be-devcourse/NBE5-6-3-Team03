package grepp.NBE5_6_2_Team03.domain.admin.userinfo;

import grepp.NBE5_6_2_Team03.api.controller.admin.userinfo.dto.UserInfoResponse;
import grepp.NBE5_6_2_Team03.domain.admin.userinfo.repository.UserInfoRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;

    public Page<UserInfoResponse> findAll(Pageable pageable) {
        Page<UserInfo> userInfos = userInfoRepository.findAll(pageable);
        return userInfos.map(this::convertToResponse);
    }

//    public List<UserInfoResponse> findAll() {
//        List<UserInfo> userInfos = userInfoRepository.findAll();
//        return userInfos.stream()
//            .map(this::convertToResponse).collect(Collectors.toList());
//    }

    public UserInfoResponse convertToResponse(UserInfo userInfo) {
        return new UserInfoResponse(
            userInfo.getId(),
            userInfo.getEmail(),
            userInfo.getName(),
            userInfo.getPhoneNumber(),
            userInfo.getRole()
        );
    }


}
