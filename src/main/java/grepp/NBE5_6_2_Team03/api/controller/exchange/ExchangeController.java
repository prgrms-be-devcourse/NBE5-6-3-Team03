package grepp.NBE5_6_2_Team03.api.controller.exchange;

import grepp.NBE5_6_2_Team03.api.controller.exchange.dto.ExchangeDto;
import grepp.NBE5_6_2_Team03.domain.exchange.entity.ExchangeRateEntity;
import grepp.NBE5_6_2_Team03.domain.exchange.repository.ExchangeRateRepository;
import grepp.NBE5_6_2_Team03.global.mapper.ExchangeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exchange")
public class ExchangeController {

    private final ExchangeRateRepository exchangeRateRepository;

    @GetMapping("/latest")
    public ExchangeDto getLatest(@RequestParam String code){
        ExchangeRateEntity entity = exchangeRateRepository.findTopByCurUnitOrderByDateDesc(code)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        log.info("{}", entity);

        return ExchangeMapper.toDto(entity);
    }

}
