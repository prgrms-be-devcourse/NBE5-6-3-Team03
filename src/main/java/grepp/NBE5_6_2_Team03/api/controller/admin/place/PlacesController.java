package grepp.NBE5_6_2_Team03.api.controller.admin.place;

import grepp.NBE5_6_2_Team03.api.controller.admin.place.dto.PlaceResponse;
import grepp.NBE5_6_2_Team03.api.controller.admin.place.dto.PlaceRequest;
import grepp.NBE5_6_2_Team03.domain.admin.place.PlaceService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/place")
public class PlacesController {
    private final PlaceService placeService;

    @GetMapping("/info")
    public String getPlaces(Model model) {
        List<PlaceResponse> places = placeService.findAll();
        List<String> countries = placeService.getCountries();
        List<String> cities = placeService.getCities();
        model.addAttribute("places", places);
        model.addAttribute("countries", countries);
        model.addAttribute("cities", cities);
        return "admin/place-info";
    }

    @GetMapping("/{id}/edit")
    public String editPlace(Model model, @PathVariable("id") String id) {
        PlaceResponse place = placeService.findById(id);
        model.addAttribute("place", place);
        return "place-edit";
    }

    @PostMapping("/{id}/edit")
    public String editPlace(Model model, @PathVariable("id") String id, PlaceRequest place,
        RedirectAttributes redirectAttributes) {
        placeService.updatePlace(id, place);

        redirectAttributes.addFlashAttribute("message", "Place updated successfully.");
        return "redirect:/place/info";
    }

    @PostMapping("/{id}/delete")
    public String deletePlace(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        placeService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Place deleted successfully.");
        return "redirect:/place/info";
    }

}