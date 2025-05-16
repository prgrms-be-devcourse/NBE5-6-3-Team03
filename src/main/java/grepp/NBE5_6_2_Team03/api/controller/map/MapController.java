package grepp.NBE5_6_2_Team03.api.controller.map;

import grepp.NBE5_6_2_Team03.domain.map.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recommend")
public class MapController {

    private final MapService mapService;


}
