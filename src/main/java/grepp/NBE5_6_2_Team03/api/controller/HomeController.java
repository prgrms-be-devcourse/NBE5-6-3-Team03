package grepp.NBE5_6_2_Team03.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@RequestParam(value = "error", required = false) String error, Model model){
        if(isLockUrl(error)){
            modelAddErrorMessage(model);
        }

        return "main";
    }

    private void modelAddErrorMessage(Model model) {
        model.addAttribute("errorMessage", "⚠ 계정이 잠겼습니다.<br> 비밀번호 찾기를 이용해 주세요");
    }

    private boolean isLockUrl(String error) {
        return "isLock".equals(error);
    }
}
