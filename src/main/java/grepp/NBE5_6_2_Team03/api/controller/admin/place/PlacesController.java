package grepp.NBE5_6_2_Team03.api.controller.admin.place;

import grepp.NBE5_6_2_Team03.domain.admin.place.PlaceService;
import grepp.NBE5_6_2_Team03.domain.admin.place.entity.Place;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
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
        List<Place> places = placeService.findAll();
        Set<String> countries = placeService.getCounties(places);
        Set<String> cities = placeService.getCities(places);
        model.addAttribute("places", places);
        model.addAttribute("countries", countries);
        model.addAttribute("cities", cities);
        return "admin/place-info";
    }
    @GetMapping("/places/{id}/edit")
    public String editPlace(Model model, @PathVariable String id) {
        Place place = placeService.findById(id);
        model.addAttribute("place", place);
        return "place-edit";
    }
    @PostMapping("/places/{id}/edit")
    public String editPlace(Model model, @PathVariable String id, Place place,
        RedirectAttributes redirectAttributes) {
        placeService.updatePlace(id, place);

        redirectAttributes.addFlashAttribute("message", "Place updated successfully.");
        return "redirect:/place/info";
    }

    @GetMapping("/places/{id}/delete")
    public String deletePlace(@PathVariable String id, RedirectAttributes redirectAttributes) {
        placeService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Place deleted successfully.");
        return "redirect:/place/info";
    }



    @GetMapping("test")
    public String test() {
        return "admin/test";
    }

}
