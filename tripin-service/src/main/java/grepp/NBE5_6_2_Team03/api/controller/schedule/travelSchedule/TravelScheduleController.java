package grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule;

import grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule.dto.request.TravelScheduleRequest;
import grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule.dto.response.TravelScheduleResponse;
import grepp.NBE5_6_2_Team03.domain.travelschedule.TravelSchedule;
import grepp.NBE5_6_2_Team03.domain.travelschedule.ScheduleStatus;
import grepp.NBE5_6_2_Team03.domain.travelschedule.service.TravelScheduleService;
import grepp.NBE5_6_2_Team03.domain.user.CustomUserDetails;
import grepp.NBE5_6_2_Team03.global.response.ApiResponse;
import grepp.NBE5_6_2_Team03.global.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

@RequestMapping("/schedule")
@RequiredArgsConstructor
@RestController
public class TravelScheduleController {

    private final TravelScheduleService travelScheduleService;

    @GetMapping
    public ApiResponse<Map<String, Object>> list(@RequestParam("travelPlanId") Long travelPlanId,
                                                 @AuthenticationPrincipal CustomUserDetails customUser) {
        Map<LocalDate, Map<ScheduleStatus, List<TravelScheduleResponse>>> groupedSchedules = travelScheduleService.getGroupedSchedules(travelPlanId);

        Map<String, Object> response = new HashMap<>();
        response.put("groupedSchedules", groupedSchedules);
        response.put("travelPlanId", travelPlanId);
        response.put("username", customUser.getUsername());

        return ApiResponse.success(response);
    }

    @GetMapping("/{travelScheduleId}")
    public ApiResponse<TravelScheduleResponse> findSchedule(@PathVariable("travelScheduleId") Long travelScheduleId,
                                                            @AuthenticationPrincipal CustomUserDetails customUser) {
        TravelSchedule schedule = travelScheduleService.findById(travelScheduleId);
        return ApiResponse.success(TravelScheduleResponse.fromEntity(customUser.getUsername(), schedule));
    }

    @PostMapping("/add")
    public ApiResponse<Object> addSchedule(@RequestParam("travelPlanId") Long travelPlanId,
                                           @RequestBody TravelScheduleRequest request,
                                           @AuthenticationPrincipal CustomUserDetails customUser) {
        try {
            TravelSchedule travelSchedule =  travelScheduleService.addSchedule(travelPlanId, request);
            return ApiResponse.success(TravelScheduleResponse.fromEntity(customUser.getUsername(), travelSchedule));
        } catch (IllegalArgumentException e) {
            return ApiResponse.error(ResponseCode.BAD_REQUEST, Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{travelScheduleId}/edit")
    public ApiResponse<Object> editSchedule(@PathVariable("travelScheduleId") Long travelScheduleId,
                                            @RequestBody TravelScheduleRequest request,
                                            @AuthenticationPrincipal CustomUserDetails customUser) {
        try {
            TravelSchedule travelSchedule =  travelScheduleService.editSchedule(travelScheduleId, request);
            return ApiResponse.success(TravelScheduleResponse.fromEntity(customUser.getUsername(), travelSchedule));
        } catch (IllegalArgumentException e) {
            return ApiResponse.error(ResponseCode.BAD_REQUEST, Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{travelScheduleId}/delete")
    public ApiResponse<Map<String, Object>> deleteSchedule(@PathVariable("travelScheduleId") Long travelScheduleId) {
        travelScheduleService.deleteSchedule(travelScheduleId);
        return ApiResponse.noContent();
    }

    @PatchMapping("/{travelScheduleId}/status")
    public ApiResponse<Map<String, Object>> scheduleStatus(@PathVariable("travelScheduleId") Long travelScheduleId) {
        ScheduleStatus scheduleStatus =  travelScheduleService.scheduleStatus(travelScheduleId);
        return ApiResponse.success(Map.of(
            "travelScheduleId", travelScheduleId,
            "scheduleStatus", scheduleStatus.name(),
            "message", "일정 상태가 변경되었습니다."
        ));
    }
}
