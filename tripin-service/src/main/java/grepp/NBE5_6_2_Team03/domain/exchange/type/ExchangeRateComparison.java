package grepp.NBE5_6_2_Team03.domain.exchange.type;

public enum ExchangeRateComparison {
    EXPENSIVE, SAME, CHEAP;

    public static ExchangeRateComparison compare(int latest, int average) {
        if (latest > average) return EXPENSIVE;
        if (latest < average) return CHEAP;
        return SAME;
    }
}
