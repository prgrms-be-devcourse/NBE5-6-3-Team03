package grepp.NBE5_6_2_Team03.domain.travelplan;

import lombok.Getter;

@Getter
public enum Country {
    JAPAN("일본", "JPY(100)"),
    THAILAND("태국", "THB");

    private final String countryName;
    private final String code;

    Country(String countryName, String code) {
        this.countryName = countryName;
        this.code = code;
    }

    public static Country fromCountryName(Country country) {
        for (Country cs : values()) {
            if (cs.countryName.equals(country)) {
                return cs;
            }
        }
        throw new IllegalArgumentException("지원하지 않는 국가입니다: " + country);
    }
}

