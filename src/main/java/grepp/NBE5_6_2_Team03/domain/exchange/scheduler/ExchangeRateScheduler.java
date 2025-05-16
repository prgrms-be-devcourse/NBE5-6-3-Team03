package grepp.NBE5_6_2_Team03.domain.exchange.scheduler;

import grepp.NBE5_6_2_Team03.api.controller.exchange.dto.ExchangeDto;
import grepp.NBE5_6_2_Team03.domain.exchange.service.ExchangeService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExchangeRateScheduler {

    private final ExchangeService exchangeService;

    private boolean isExistExchange(ExchangeDto[] rates){
        return rates == null || rates.length == 0;
    }

    @Scheduled(initialDelay = 0, fixedRate=1000000)     // TODO 이거 한번 데이터 받은 이후에 주석처리 해야합니다.
    public void fetchExchangeRates() {

        try {
            log.info("Fetch exchange rates start");

            LocalDate today = LocalDate.now();
            String searchDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            ExchangeDto[] rates = exchangeService.getCurrentExchanges(today);

            if (isExistExchange(rates)) {
                log.info("Not Exist Exchange Rate Data Now");
                return;
            }

            exchangeService.saveAllExchangeRates(rates,searchDate);

            log.info("Fetch exchange rates done");

        } catch(Exception e){
            log.error("Cannot get exchange rates", e);
        }

    }

}
