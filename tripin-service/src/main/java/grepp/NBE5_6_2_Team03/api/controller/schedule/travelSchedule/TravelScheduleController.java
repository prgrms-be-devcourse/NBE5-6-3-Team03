package grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule;

import grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule.dto.request.TravelScheduleRequest;
import grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule.dto.request.TravelScheduleStatusRequest;
import grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule.dto.response.TravelScheduleResponse;
import grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule.dto.response.TravelScheduleStatusResponse;
import grepp.NBE5_6_2_Team03.domain.travelschedule.TravelSchedule;
import grepp.NBE5_6_2_Team03.domain.travelschedule.service.TravelScheduleService;
import grepp.NBE5_6_2_Team03.global.response.ApiResponse;
import grepp.NBE5_6_2_Team03.global.response.ResponseCode;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

@RequestMapping("/travel-plans/{travelPlanId}/schedule")
@RequiredArgsConstructor
@RestController
public class TravelScheduleController {

    private final TravelScheduleService travelScheduleService;

    @GetMapping
    public ApiResponse<Map<String, Object>> list(@PathVariable("travelPlanId") Long travelPlanId) {
        Map<LocalDate, List<TravelScheduleResponse>> groupedSchedules = travelScheduleService.getGroupedSchedules(travelPlanId);

        Map<String, Object> response = new HashMap<>();
        response.put("groupedSchedules", groupedSchedules);
        response.put("travelPlanId", travelPlanId);

        return ApiResponse.success(response);
    }

    @GetMapping("/{travelScheduleId}")
    public ApiResponse<TravelScheduleResponse> findSchedule(@PathVariable("travelPlanId") Long travelPlanId,
                                                            @PathVariable("travelScheduleId") Long travelScheduleId) {
        TravelSchedule schedule = travelScheduleService.findById(travelScheduleId);
        return ApiResponse.success(TravelScheduleResponse.fromEntity(schedule));
    }

    @PostMapping("/new")
    public ApiResponse<Object> createSchedule(@PathVariable("travelPlanId") Long travelPlanId,
                                              @RequestBody TravelScheduleRequest request) {
        try {
            TravelSchedule travelSchedule =  travelScheduleService.createSchedule(travelPlanId, request);
            return ApiResponse.success(TravelScheduleResponse.fromEntity(travelSchedule));
        } catch (IllegalArgumentException e) {
            return ApiResponse.error(ResponseCode.BAD_REQUEST, Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{travelScheduleId}")
    public ApiResponse<Object> editSchedule(@PathVariable("travelPlanId") Long travelPlanId,
                                            @PathVariable("travelScheduleId") Long travelScheduleId,
                                            @RequestBody TravelScheduleRequest request) {
        try {
            TravelSchedule travelSchedule =  travelScheduleService.editSchedule(travelScheduleId, request);
            return ApiResponse.success(TravelScheduleResponse.fromEntity(travelSchedule));
        } catch (IllegalArgumentException e) {
            return ApiResponse.error(ResponseCode.BAD_REQUEST, Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{travelScheduleId}")
    public ApiResponse<Map<String, Object>> deleteSchedule(@PathVariable("travelPlanId") Long travelPlanId,
                                                           @PathVariable("travelScheduleId") Long travelScheduleId) {
        travelScheduleService.deleteSchedule(travelScheduleId);
        return ApiResponse.noContent();
    }

    @PatchMapping("/{travelScheduleId}/status")
    public ApiResponse<TravelScheduleStatusResponse> editScheduleStatus(@PathVariable("travelPlanId") Long travelPlanId,
                                                                        @PathVariable("travelScheduleId") Long travelScheduleId,
                                                                        @RequestBody TravelScheduleStatusRequest request) {
        TravelSchedule travelSchedule = travelScheduleService.editScheduleStatus(travelScheduleId, request);
        return ApiResponse.success(TravelScheduleStatusResponse.fromEntity(travelSchedule));
    }
}
