package grepp.NBE5_6_2_Team03.domain.exchange.scheduler;

import grepp.NBE5_6_2_Team03.api.controller.exchange.dto.ExchangeDto;
import grepp.NBE5_6_2_Team03.domain.exchange.entity.ExchangeRateEntity;
import grepp.NBE5_6_2_Team03.domain.exchange.repository.ExchangeRateRepository;
import grepp.NBE5_6_2_Team03.domain.exchange.service.ExchangeService;
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

    @Scheduled(cron = "0 1 11 * * *")
    public void fetchExchangeRates() {
        log.info("Fetch exchange rates start");

        ExchangeDto[] rates = exchangeService.getCurrentExchanges();

        Arrays.stream(rates).forEach(dto -> {
            ExchangeRateEntity entity = ExchangeRateEntity.builder()
                .curUnit(dto.getCurUnit())
                .curName(dto.getCurName())
                .baseRate(dto.getBaseRate())
                .ttbRate(dto.getTtbRate())
                .ttsRate(dto.getTtsRate())
                .date(dto.getDate())
                .build();

            exchangeRateRepository.save(entity);
        });

        log.info("Fetch exchange rates done");
    }

}
