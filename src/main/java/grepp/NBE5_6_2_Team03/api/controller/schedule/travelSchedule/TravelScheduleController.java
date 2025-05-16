package grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule;

import grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule.dto.request.TravelScheduleRequest;
import grepp.NBE5_6_2_Team03.domain.schedule.travelschedule.TravelSchedule;
import grepp.NBE5_6_2_Team03.domain.schedule.travelschedule.code.ScheduleStatus;
import grepp.NBE5_6_2_Team03.domain.schedule.travelschedule.service.TravelScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/plan/{travelPlanId}/schedule")
public class TravelScheduleController {

    private final TravelScheduleService travelScheduleService;

    @GetMapping("/add")
    public String addForm(@PathVariable Long travelPlanId, Model model) {
        model.addAttribute("travelPlanId", travelPlanId);
        model.addAttribute("request", new TravelScheduleRequest());
        return "schedule/schedule-form";
    }

    @PostMapping("/add")
    public String addSchedule(@PathVariable Long travelPlanId,
                              @ModelAttribute TravelScheduleRequest request,
                              Model model) {
        model.addAttribute("travelPlanId", travelPlanId);

        try {
            travelScheduleService.addSchedule(travelPlanId, request);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("request", request);
            return "schedule/schedule-form";
        }

        return "redirect:/plan/" + travelPlanId + "/schedule/list";
    }

    @GetMapping("/{travelScheduleId}/edit")
    public String editForm(@PathVariable Long travelPlanId,
                           @PathVariable Long travelScheduleId,
                           Model model) {
        TravelSchedule schedule = travelScheduleService.findById(travelScheduleId);

        model.addAttribute("travelPlanId", travelPlanId);
        model.addAttribute("travelScheduleId", travelScheduleId);
        model.addAttribute("request", TravelScheduleRequest.fromEntity(schedule));
        return "schedule/schedule-form";
    }

    @PostMapping("/{travelScheduleId}/edit")
    public String editSchedule(@PathVariable Long travelPlanId,
                               @PathVariable Long travelScheduleId,
                               @ModelAttribute TravelScheduleRequest request) {
        travelScheduleService.editSchedule(travelScheduleId, request);
        return "redirect:/plan/" + travelPlanId + "/schedule/list";
    }

    @PostMapping("/{travelScheduleId}/delete")
    public String deleteSchedule(@PathVariable Long travelPlanId,
                                 @PathVariable Long travelScheduleId) {
        travelScheduleService.deleteSchedule(travelScheduleId);
        return "redirect:/plan/" + travelPlanId + "/schedule/list";
    }

    @GetMapping("/list")
    public String list(@PathVariable Long travelPlanId, Model model) {
        Map<LocalDate, Map<ScheduleStatus, List<TravelSchedule>>> groupedSchedules = travelScheduleService.getGroupedSchedules(travelPlanId);
        model.addAttribute("groupedSchedules", groupedSchedules);
        model.addAttribute("travelPlanId", travelPlanId);
        return "schedule/schedule-list";
    }

    @PostMapping("/{travelScheduleId}/status")
    public String scheduleStatus(@PathVariable Long travelPlanId,
                                 @PathVariable Long travelScheduleId) {
        travelScheduleService.scheduleStatus(travelScheduleId);
        return "redirect:/plan/" + travelPlanId + "/schedule/list";
    }
}
