package grepp.NBE5_6_2_Team03.api.controller.exchange;

import grepp.NBE5_6_2_Team03.api.controller.exchange.dto.ExchangeListResponse;
import grepp.NBE5_6_2_Team03.api.controller.exchange.dto.ExchangeResponse;
import grepp.NBE5_6_2_Team03.domain.exchange.service.ExchangeService;
import grepp.NBE5_6_2_Team03.global.response.ApiResponse;
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

    @GetMapping("/monthly-exchange")
    public ApiResponse<ExchangeListResponse> getMonthlyExchangeRates() {
        ExchangeListResponse aa = ExchangeListResponse.from(exchangeService.findAllExchanges());
        return ApiResponse.success(aa);
    }

}
