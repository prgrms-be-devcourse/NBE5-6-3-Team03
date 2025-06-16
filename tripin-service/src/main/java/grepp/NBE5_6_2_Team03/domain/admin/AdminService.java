package grepp.NBE5_6_2_Team03.domain.admin;

import grepp.NBE5_6_2_Team03.api.controller.admin.dto.statistic.CountriesStatisticResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.statistic.MonthlyStatisticResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.user.UserDetailResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.user.UserInfoResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.user.UserModifyRequest;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.user.UserSearchPageResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.user.UserSearchRequest;
import grepp.NBE5_6_2_Team03.domain.adjustment.respository.AdjustmentRepository;
import grepp.NBE5_6_2_Team03.domain.travelplan.repository.TravelPlanRepository;
import grepp.NBE5_6_2_Team03.domain.user.User;
import grepp.NBE5_6_2_Team03.domain.user.repository.UserQueryRepository;
import grepp.NBE5_6_2_Team03.domain.user.repository.UserRepository;
import grepp.NBE5_6_2_Team03.global.exception.CannotModifyAdminException;
import grepp.NBE5_6_2_Team03.global.exception.NotFoundException;
import grepp.NBE5_6_2_Team03.global.message.ExceptionMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {
    private final UserRepository userRepository;
    private final UserQueryRepository userQueryRepository;
    private final TravelPlanRepository travelPlanRepository;
    private final AdjustmentRepository travelPlanQueryRepository;

    public UserSearchPageResponse findUsersPage(UserSearchRequest userSearchRequest) {
        Page<UserInfoResponse> userInfoResponsePage =
                userQueryRepository.findUsersPage(userSearchRequest.getEmail(), userSearchRequest.getIsLocked(), userSearchRequest.getPageable())
                .map(UserInfoResponse::of);

        return UserSearchPageResponse.from(userInfoResponsePage);
    }

    @Transactional
    public void updateUserInfo(UserModifyRequest request, Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ExceptionMessage.USER_NOT_FOUND)
        );

        user.modifyName(request.getName());
        user.modifyPhoneNumber(request.getPhoneNumber());
        user.modifyIsLocked(request.getIsLocked());
    }

    @Transactional
    public void deleteUserById(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(ExceptionMessage.USER_NOT_FOUND));
        if (user.isAdmin()) {
            throw new CannotModifyAdminException(ExceptionMessage.ADMIN_NOT_MODIFIED);
        }
        travelPlanRepository.deleteByUserId(userId);
        userRepository.delete(user);
    }

    public List<MonthlyStatisticResponse> getMonthStatistics() {
        List<MonthlyStatisticResponse> projections = travelPlanQueryRepository.getMonthStatistics();
        return projections.stream()
                .map(p -> new MonthlyStatisticResponse(p.getMonth(), p.getCount()))
                .collect(Collectors.toList());
    }

    public List<CountriesStatisticResponse> getCountriesStatistics() {
        return travelPlanQueryRepository.getCountriesStatistics();
    }

    public boolean isDuplicatedEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean isDuplicatedUsername(String username) {
        return userRepository.findByName(username).isPresent();
    }

    public UserDetailResponse findById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(ExceptionMessage.USER_NOT_FOUND));
        return UserDetailResponse.of(user);
    }

}