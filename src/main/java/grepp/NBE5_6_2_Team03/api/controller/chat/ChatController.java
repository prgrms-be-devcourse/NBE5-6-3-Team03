package grepp.NBE5_6_2_Team03.api.controller.chat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

    @GetMapping("/trip-chat")
    public String chatPage(){
        return "trip/trip-chat";
    }

}
