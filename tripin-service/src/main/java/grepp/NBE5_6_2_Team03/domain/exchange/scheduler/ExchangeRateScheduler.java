package grepp.NBE5_6_2_Team03.domain.exchange.scheduler;

import grepp.NBE5_6_2_Team03.api.controller.exchange.dto.ExchangeResponse;
import grepp.NBE5_6_2_Team03.domain.exchange.service.ExchangeService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExchangeRateScheduler {

    private final ExchangeService exchangeService;

    private boolean isEmptyExchange(ExchangeResponse[] exchanges){
        return exchanges == null || exchanges.length == 0;
    }

//    @Scheduled(initialDelay = 0, fixedRate=1000000)     // TODO 이거 한번 데이터 받은 이후에 주석처리 해야합니다.
    public void fetchExchangeRates() {
        log.info("Fetch exchange rates start");
        try {
            LocalDate today = LocalDate.now();
//            ExchangeResponse[] exchanges = exchangeService.getCurrentExchanges(today);
            List<ExchangeResponse[]> exchangesList = exchangeService.getMonthlyExchanges(today);

            for (int i = 0; i < exchangesList.size(); i++) {
                ExchangeResponse[] dailyExchanges = exchangesList.get(i);
                LocalDate dateToSave = today.minusDays(29 - i);

                if (isEmptyExchange(dailyExchanges)) {
                    log.info("No Exchange Rate Data for " + dateToSave.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    continue;
                }

                String formattedDateToSave = dateToSave.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                exchangeService.saveAllExchangeRates(dailyExchanges, formattedDateToSave);
            }

            log.info("Fetch exchange exchanges done");
        } catch(Exception e){
            log.error("Cannot get exchange rates", e);
        }
    }
}
