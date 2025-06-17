package grepp.NBE5_6_2_Team03.api.controller.schedule.traveltimeai.dto;

import lombok.Data;

@Data
public class TravelTimeRequest {
    private String departure;
    private String destination;
    private String transport;

    public TravelTimeRequest(String departure, String destination, String transport) {
        this.departure = departure;
        this.destination = destination;
        this.transport = transport;
    }


}
