package grepp.NBE5_6_2_Team03.domain.admin.place.parser;

import com.fasterxml.jackson.databind.JsonNode;

public interface AddressParser {
    String parsingCountry(JsonNode addressComponents);
    String parsingCity(JsonNode addressComponents);
    String extractByType(JsonNode components, String targetType);
}
