package grepp.NBE5_6_2_Team03.domain.admin;

import grepp.NBE5_6_2_Team03.api.controller.admin.dto.statistic.CountriesStatisticResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.statistic.MonthlyStatisticResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.user.UserInfoResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.user.UserInfoUpdateRequest;
import grepp.NBE5_6_2_Team03.api.controller.admin.dto.user.UserSearchRequest;
import grepp.NBE5_6_2_Team03.domain.admin.code.LockStatus;
import grepp.NBE5_6_2_Team03.domain.travelplan.repository.TravelPlanRepository;
import grepp.NBE5_6_2_Team03.domain.user.User;
import grepp.NBE5_6_2_Team03.domain.user.repository.UserRepository;
import grepp.NBE5_6_2_Team03.global.exception.CannotModifyAdminException;
import grepp.NBE5_6_2_Team03.global.exception.Message;
import grepp.NBE5_6_2_Team03.global.exception.NotFoundException;
import java.util.ArrayList;
import java.util.List;
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
    private final TravelPlanRepository travelPlanRepository;

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
            userInfo.isLocked(),
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

    public boolean isDuplicatedEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean isDuplicatedUsername(String username) {
        return userRepository.findByName(username).isPresent();
    }

    public Page<UserInfoResponse> findUsersPage(UserSearchRequest userSearchRequest) {
        boolean isLocked = userSearchRequest.isLocked();
        Pageable pageable = userSearchRequest.getPageable();

        Page<User> lockedUserInfos = userRepository.findUserWithOption(isLocked, pageable);
        return lockedUserInfos.map(this::convertToResponse);
    }

    @Transactional
    public void lockUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(Message.USER_NOT_FOUND));
        user.updateIsLocked(true);
    }

    @Transactional
    public void unLockUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(Message.USER_NOT_FOUND));
        user.updateIsLocked(false);
    }

    @Transactional
    public void deleteById(Long id) {
        if(userRepository.isAdmin(id)) {
            throw new NotFoundException(Message.ADMIN_NOT_DELETE);
        }
        travelPlanRepository.deleteByUserId(id);
        userRepository.deleteById(id);
    }

    public List<MonthlyStatisticResponse> getMonthStatistics() {
        List<Object[]> monthlyStatsRaw = travelPlanRepository.getMonthStatistics();
        List<MonthlyStatisticResponse> monthlyStatisticResponses = new ArrayList<>();
        for(Object[] obj : monthlyStatsRaw) {
            int month = (Integer) obj[0];
            long count = (Long) obj[1];
            monthlyStatisticResponses.add(new MonthlyStatisticResponse(month, count));
        }
        return monthlyStatisticResponses;
    }

    public List<CountriesStatisticResponse> getCountriesStatistics() {
        List<Object[]> monthlyStatisticsMap = travelPlanRepository.getCountriesStatistics();
        List<CountriesStatisticResponse> countriesStatisticResponses = new ArrayList<>();
        for(Object[] obj : monthlyStatisticsMap) {
            String country = (String) obj[0];
            long count = (Long) obj[1];
            countriesStatisticResponses.add(new CountriesStatisticResponse(country, count));
        }
        return countriesStatisticResponses;
    }

    public String changeLockStatus(Long id) {
        if (userRepository.isAdmin(id)) throw new CannotModifyAdminException(Message.ADMIN_NOT_MODIFIED);

        User user = userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(Message.USER_NOT_FOUND));

        if (user.isLocked()) {
            unLockUser(id);
            return LockStatus.UNLOCKED.name();
        }
        else {
            lockUser(id);
            return LockStatus.LOCKED.name();
        }
    }
}