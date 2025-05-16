package grepp.NBE5_6_2_Team03.domain.exchange.service;

import grepp.NBE5_6_2_Team03.api.controller.exchange.dto.ExchangeResponse;
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

    public ExchangeResponse[] getCurrentExchanges(LocalDate today) {
        String accessUrl = createAccessUrl(today);
        ResponseEntity<ExchangeResponse[]> exchangeResponses = restTemplate.getForEntity(accessUrl, ExchangeResponse[].class);

        return exchangeResponses.getBody();
    }
    public ExchangeResponse getLatest(String curUnit){
        ExchangeRateEntity entity = exchangeRateRepository.findLatestByCurUnit(curUnit)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ExchangeResponse.fromDto(entity);
    }
    public void saveAllExchangeRates(ExchangeResponse[] exchanges, String searchDate) {
        List<ExchangeRateEntity> entities = convertToEntities(exchanges, searchDate);
        exchangeRateRepository.saveAll(entities);
    }
    private String createAccessUrl(LocalDate today) {
        String formattedToday = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return String.format("%s?authkey=%s&apikey=%s", apiUrl, apiKey,formattedToday);
    }
    private List<ExchangeRateEntity> convertToEntities(ExchangeResponse[] exchanges, String searchDate) {
        return Arrays.stream(exchanges)
            .map(request -> request.toEntity(searchDate))
            .collect(Collectors.toList());
    }
}