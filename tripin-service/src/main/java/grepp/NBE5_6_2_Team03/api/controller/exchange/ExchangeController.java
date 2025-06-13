package grepp.NBE5_6_2_Team03.api.controller.exchange;

import grepp.NBE5_6_2_Team03.api.controller.exchange.dto.ExchangeResponse;
import grepp.NBE5_6_2_Team03.domain.exchange.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exchange")
public class ExchangeController {

    private final ExchangeService exchangeService;

    @GetMapping("/latest")
    public ExchangeResponse getLatestRate(@RequestParam("curUnit") String curUnit){
        return exchangeService.getLatest(curUnit);
    }
}
