package grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule;

import grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule.dto.request.TravelScheduleRequest;
import grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule.dto.response.TravelScheduleResponse;
import grepp.NBE5_6_2_Team03.domain.travelschedule.TravelSchedule;
import grepp.NBE5_6_2_Team03.domain.travelschedule.ScheduleStatus;
import grepp.NBE5_6_2_Team03.domain.travelschedule.service.TravelScheduleService;
import grepp.NBE5_6_2_Team03.domain.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plan/{travelPlanId}/schedule")
public class TravelScheduleController {

    private final TravelScheduleService travelScheduleService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> list(@PathVariable("travelPlanId") Long travelPlanId,
                               @AuthenticationPrincipal CustomUserDetails customUser) {
        Map<LocalDate, Map<ScheduleStatus, List<TravelScheduleResponse>>> groupedSchedules = travelScheduleService.getGroupedSchedules(travelPlanId);

        Map<String, Object> response = new HashMap<>();
        response.put("groupedSchedules", groupedSchedules);
        response.put("travelPlanId", travelPlanId);
        response.put("username", customUser.getUsername());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{travelScheduleId}")
    public ResponseEntity<?> findSchedule(@PathVariable("travelPlanId") Long travelPlanId,
                                          @PathVariable("travelScheduleId") Long travelScheduleId) {
        TravelSchedule schedule = travelScheduleService.findById(travelScheduleId);
        return ResponseEntity.ok(TravelScheduleResponse.fromEntity(schedule));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addSchedule(@PathVariable("travelPlanId") Long travelPlanId,
                              @RequestBody TravelScheduleRequest request) {
        try {
            TravelSchedule travelSchedule =  travelScheduleService.addSchedule(travelPlanId, request);
            return ResponseEntity.ok(TravelScheduleResponse.fromEntity(travelSchedule));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/{travelScheduleId}/edit")
    public ResponseEntity<?> editSchedule(@PathVariable("travelPlanId") Long travelPlanId,
                               @PathVariable("travelScheduleId") Long travelScheduleId,
                               @RequestBody TravelScheduleRequest request) {
        try {
            TravelSchedule travelSchedule =  travelScheduleService.editSchedule(travelScheduleId, request);
            return ResponseEntity.ok(TravelScheduleResponse.fromEntity(travelSchedule));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/{travelScheduleId}/delete")
    public ResponseEntity<?> deleteSchedule(@PathVariable("travelPlanId") Long travelPlanId,
                                 @PathVariable("travelScheduleId") Long travelScheduleId) {
        travelScheduleService.deleteSchedule(travelScheduleId);
        return ResponseEntity.ok(Map.of(
            "travelScheduleId", travelScheduleId,
            "message", "일정이 삭제되었습니다."
        ));
    }

    @PostMapping("/{travelScheduleId}/status")
    public ResponseEntity<?> scheduleStatus(@PathVariable("travelPlanId") Long travelPlanId,
                                 @PathVariable("travelScheduleId") Long travelScheduleId) {
        ScheduleStatus scheduleStatus =  travelScheduleService.scheduleStatus(travelScheduleId);
        return ResponseEntity.ok(Map.of(
            "travelScheduleId", travelScheduleId,
            "scheduleStatus", scheduleStatus.name(),
            "message", "일정 상태가 변경되었습니다."
        ));
    }
}
