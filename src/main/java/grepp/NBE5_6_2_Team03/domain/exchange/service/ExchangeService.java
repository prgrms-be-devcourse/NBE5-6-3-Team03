package grepp.NBE5_6_2_Team03.domain.exchange.service;

import grepp.NBE5_6_2_Team03.api.controller.exchange.dto.ExchangeDto;
import grepp.NBE5_6_2_Team03.domain.exchange.entity.ExchangeRateEntity;
import grepp.NBE5_6_2_Team03.domain.exchange.repository.ExchangeRateRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ExchangeRateRepository exchangeRateRepository;

    @Value("${exchange.api.key}")
    private String apiKey;

    @Value("${exchange.api.url}")
    private String apiUrl;


    public ExchangeDto[] getCurrentExchanges(LocalDate date) {

        String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String url = String.format("%s?authkey=%s&searchdate=%s&data=AP01",apiUrl,apiKey,formattedDate);

        ResponseEntity<ExchangeDto[]> exchangeResponse = restTemplate.getForEntity(url, ExchangeDto[].class);

        return exchangeResponse.getBody();
    }

    public ExchangeDto getLatest(String code){

        ExchangeRateEntity entity = exchangeRateRepository.findLatestByCurUnit(code)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ExchangeDto.toDto(entity);
    }

    public void saveAllExchangeRates(ExchangeDto[] rates, String searchDate) {

        List<ExchangeRateEntity> entities = Arrays.stream(rates)
            .map(dto -> dto.toEntity(searchDate))
            .collect(Collectors.toList());

        exchangeRateRepository.saveAll(entities);
    }

}