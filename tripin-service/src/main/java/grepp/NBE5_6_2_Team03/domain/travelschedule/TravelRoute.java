package grepp.NBE5_6_2_Team03.domain.travelschedule;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class TravelRoute {

    private String departure;
    private String destination;
    private String transportation;

    protected TravelRoute() {
    }

    public TravelRoute(String departure, String destination, String transportation) {
        this.departure = departure;
        this.destination = destination;
        this.transportation = transportation;
    }
}
