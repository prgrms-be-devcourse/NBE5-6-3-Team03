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

    @Scheduled(initialDelay = 0, fixedRate=1000000)     // TODO 이거 한번 데이터 받은 이후에 주석처리 해야합니다.
    public void fetchExchangeRates() {
        try{
            log.info("Fetch exchange rates start");

            ExchangeDto[] rates = exchangeService.getCurrentExchanges();

            if(rates == null || rates.length == 0) {
                log.info("현재 환율 데이터가 없습니다.");
                return;
        }

        Arrays.stream(rates).forEach(dto -> {
            ExchangeRateEntity entity = ExchangeRateEntity.builder()
                .curUnit(dto.getCurUnit())
                .curName(dto.getCurName())
                .baseRate(dto.getBaseRate())
                .ttbRate(dto.getTtbRate())
                .ttsRate(dto.getTtsRate())
                .build();

            log.info("{}", entity);

            exchangeRateRepository.save(entity);

        });

            log.info("Fetch exchange rates done");

        }catch(Exception e){
            log.error("환율 수집 중 에러 발생", e);
        }

    }

}
