package grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule;

import grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule.dto.request.TravelScheduleRequest;
import grepp.NBE5_6_2_Team03.domain.travelschedule.TravelSchedule;
import grepp.NBE5_6_2_Team03.domain.travelschedule.ScheduleStatus;
import grepp.NBE5_6_2_Team03.domain.travelschedule.service.TravelScheduleService;
import grepp.NBE5_6_2_Team03.domain.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/plan/{travelPlanId}/schedule")
public class TravelScheduleController {

    private final TravelScheduleService travelScheduleService;

    @GetMapping
    public String list(@PathVariable("travelPlanId") Long travelPlanId,
                       @AuthenticationPrincipal CustomUserDetails customUser,
                       Model model) {
        Map<LocalDate, Map<ScheduleStatus, List<TravelSchedule>>> groupedSchedules = travelScheduleService.getGroupedSchedules(travelPlanId);
        model.addAttribute("groupedSchedules", groupedSchedules);
        model.addAttribute("travelPlanId", travelPlanId);
        model.addAttribute("username", customUser.getUsername());
        return "schedule/schedule-list";
    }

    @GetMapping("/add")
    public String addForm(@PathVariable("travelPlanId") Long travelPlanId,
                          @AuthenticationPrincipal CustomUserDetails customUser,
                          Model model) {
        model.addAttribute("travelPlanId", travelPlanId);
        model.addAttribute("username", customUser.getUsername());
        model.addAttribute("request", new TravelScheduleRequest());
        return "schedule/schedule-form";
    }

    @PostMapping("/add")
    public String addSchedule(@PathVariable("travelPlanId") Long travelPlanId,
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

        return "redirect:/plan/" + travelPlanId + "/schedule";
    }

    @GetMapping("/{travelScheduleId}/edit")
    public String editForm(@PathVariable("travelPlanId") Long travelPlanId,
                           @PathVariable("travelScheduleId") Long travelScheduleId,
                           @AuthenticationPrincipal CustomUserDetails customUser,
                           Model model) {
        TravelSchedule schedule = travelScheduleService.findById(travelScheduleId);

        model.addAttribute("travelPlanId", travelPlanId);
        model.addAttribute("travelScheduleId", travelScheduleId);
        model.addAttribute("username", customUser.getUsername());
        model.addAttribute("request", TravelScheduleRequest.fromEntity(schedule));
        return "schedule/schedule-form";
    }

    @PostMapping("/{travelScheduleId}/edit")
    public String editSchedule(@PathVariable("travelPlanId") Long travelPlanId,
                               @PathVariable("travelScheduleId") Long travelScheduleId,
                               @ModelAttribute TravelScheduleRequest request,
                               Model model) {
        model.addAttribute("travelPlanId", travelPlanId);

        try {
            travelScheduleService.editSchedule(travelScheduleId, request);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("request", request);
            return "schedule/schedule-form";
        }

        return "redirect:/plan/" + travelPlanId + "/schedule";
    }

    @PostMapping("/{travelScheduleId}/delete")
    public String deleteSchedule(@PathVariable("travelPlanId") Long travelPlanId,
                                 @PathVariable("travelScheduleId") Long travelScheduleId) {
        travelScheduleService.deleteSchedule(travelScheduleId);
        return "redirect:/plan/" + travelPlanId + "/schedule";
    }

    @PostMapping("/{travelScheduleId}/status")
    public String scheduleStatus(@PathVariable("travelPlanId") Long travelPlanId,
                                 @PathVariable("travelScheduleId") Long travelScheduleId) {
        travelScheduleService.scheduleStatus(travelScheduleId);
        return "redirect:/plan/" + travelPlanId + "/schedule";
    }
}
