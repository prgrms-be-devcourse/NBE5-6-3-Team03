package grepp.NBE5_6_2_Team03.global.dataseed.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class TranslationService {

    private Map<String, String> translations;

    @PostConstruct
    public void init() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("translate.json");
        if (inputStream != null) {
            translations = objectMapper.readValue(inputStream, Map.class);
        }
    }

    public String getTranslatedName(String originName) {
        return translations.getOrDefault(originName, originName);  // 없으면 원본 그대로 반환
    }
}
