package grepp.NBE5_6_2_Team03.domain.exchange.service;

import grepp.NBE5_6_2_Team03.api.controller.exchange.dto.ExchangeListResponse;
import grepp.NBE5_6_2_Team03.api.controller.exchange.dto.ExchangeResponse;
import grepp.NBE5_6_2_Team03.domain.exchange.entity.ExchangeRateEntity;
import grepp.NBE5_6_2_Team03.domain.exchange.repository.ExchangeRateQueryRepository;
import grepp.NBE5_6_2_Team03.domain.exchange.repository.ExchangeRateRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import grepp.NBE5_6_2_Team03.domain.exchange.type.ExchangeRateComparison;
import java.util.stream.Stream;
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
    private final ExchangeRateQueryRepository exchangeRateQueryRepository;

    @Value("${exchange.api.key}")
    private String apiKey;

    @Value("${exchange.api.url}")
    private String apiUrl;

    public ExchangeResponse[] getCurrentExchanges(LocalDate today) {
        String accessUrl = createAccessUrl(today);
        ResponseEntity<ExchangeResponse[]> exchangeResponses = restTemplate.getForEntity(accessUrl, ExchangeResponse[].class);
        List<ExchangeResponse> filteredList = Optional.ofNullable(exchangeResponses.getBody()).stream().flatMap(Arrays::stream).filter(
            e -> "KRW".equals(e.getCurUnit()) || "JPY(100)".equals(e.getCurUnit()) || "THB".equals(e.getCurUnit())
        ).toList();
//        return exchangeResponses.getBody();
        return filteredList.toArray(new ExchangeResponse[0]);
    }

    public ExchangeResponse getLatest(String curUnit){
        ExchangeRateEntity entity = exchangeRateRepository.findTop1ByCurUnitOrderByDateDesc(curUnit)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ExchangeResponse.fromDto(entity);
    }

    public void saveAllExchangeRates(ExchangeResponse[] exchanges, String formattedToday) {
        List<ExchangeRateEntity> entities = convertToEntities(exchanges, formattedToday);
        exchangeRateRepository.saveAll(entities);
    }

    private String createAccessUrl(LocalDate today) {
        String formattedToday = today.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return String.format("%s?authkey=%s&searchdate=%s&data=AP01", apiUrl, apiKey,formattedToday);
    }

    private List<ExchangeRateEntity> convertToEntities(ExchangeResponse[] exchanges, String formattedToday) {
        return Arrays.stream(exchanges)
            .map(request -> request.toEntity(formattedToday))
            .collect(Collectors.toList());
    }

    public int getRecentAverageRate(String curUnit) {
        return exchangeRateQueryRepository.getRecentAverageRate(curUnit);
    }

    public ExchangeRateComparison compareLatestRateToAverageRate(String curUnit) {
        int latest = getLatestExchangeRateInt(curUnit);
        int average = getRecentAverageRate(curUnit);

        return ExchangeRateComparison.compare(latest, average);
    }

    public int exchangeToWon(String curUnit, int foreignCurrency) {
        ExchangeResponse response = getLatest(curUnit);
        double perUnitRate = getPerUnitRate(curUnit, response.getBaseRate());

        return (int) (foreignCurrency * perUnitRate);
    }

    public int exchangeToForeign(String curUnit, int wonCurrency) {
        ExchangeResponse response = getLatest(curUnit);
        double perUnitRate = getPerUnitRate(curUnit, response.getBaseRate());

        return (int) (wonCurrency / perUnitRate);
    }

    public int getLatestExchangeRateInt(String curUnit) {
        return (int) Double.parseDouble(getLatest(curUnit).getBaseRate().replace(",", ""));
    }

    private double getPerUnitRate(String curUnit, String foreignCurrency) {
        double baseRate = Double.parseDouble(foreignCurrency.replace(",", ""));
        return curUnit.endsWith("(100)") ? baseRate / 100.0 : baseRate;
    }

    public List<ExchangeResponse[]> getMonthlyExchanges(LocalDate today) {
        return Stream.iterate(today.minusDays(29), date -> date.plusDays(1))
            .limit(30)
            .map(this::getCurrentExchanges)
            .collect(Collectors.toList());
    }

    public ExchangeListResponse findAllExchanges() {
        List<ExchangeResponse> list = exchangeRateRepository.findAll()
            .stream()
            .map(ExchangeResponse::fromDto)
            .collect(Collectors.toList());
        return new ExchangeListResponse(list);
    }

}
