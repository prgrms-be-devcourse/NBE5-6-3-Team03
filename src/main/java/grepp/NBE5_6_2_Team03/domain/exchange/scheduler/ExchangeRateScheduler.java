package grepp.NBE5_6_2_Team03.domain.exchange.scheduler;

import grepp.NBE5_6_2_Team03.api.controller.exchange.dto.ExchangeDto;
import grepp.NBE5_6_2_Team03.domain.exchange.repository.ExchangeRateRepository;
import grepp.NBE5_6_2_Team03.domain.exchange.service.ExchangeService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExchangeRateScheduler {

    private final ExchangeService exchangeService;
    private final ExchangeRateRepository exchangeRateRepository;

    @Scheduled(initialDelay = 0, fixedRate=1000000)     // TODO 이거 한번 데이터 받은 이후에 주석처리 해야합니다.
    public void fetchExchangeRates() {

        try {
            log.info("Fetch exchange rates start");

            ExchangeDto[] rates = exchangeService.getCurrentExchanges();

            boolean isExistData = rates == null || rates.length == 0;

            if (isExistData) {
                log.info("Not Exist Exchange Rate Data Now");
                return;
            }

            String searchDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            Arrays.stream(rates)
                .map(dto -> dto.toEntity(searchDate))
                .forEach(exchangeRateRepository::save);

            log.info("Fetch exchange rates done");

        } catch(Exception e){
            log.error("환율 수집 중 에러 발생", e);
        }

    }

}
