package grepp.NBE5_6_2_Team03.api.controller.mail;

import grepp.NBE5_6_2_Team03.domain.mail.service.MailService;
import grepp.NBE5_6_2_Team03.domain.user.User;
import grepp.NBE5_6_2_Team03.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {

    private final MailService mailService;
    private final UserRepository userRepository;

    @PostMapping("/send")
    public String sendMail(@AuthenticationPrincipal User user) {

        String email = user.getEmail();




    }

}
