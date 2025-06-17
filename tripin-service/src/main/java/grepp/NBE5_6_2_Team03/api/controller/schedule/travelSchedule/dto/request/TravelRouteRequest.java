package grepp.NBE5_6_2_Team03.api.controller.schedule.travelSchedule.dto.request;

import grepp.NBE5_6_2_Team03.api.controller.schedule.traveltimeai.dto.TravelTimeRequest;
import grepp.NBE5_6_2_Team03.api.controller.schedule.traveltimeai.dto.TravelTimeResponse;
import grepp.NBE5_6_2_Team03.domain.schedule.treveltimeai.service.TravelTimeAiService;
import grepp.NBE5_6_2_Team03.domain.travelschedule.TravelRoute;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TravelRouteRequest {

    private String departure;
    private String destination;
    private String transportation;
    private String expectedTime;

    public TravelRouteRequest() {
    }

    @Builder
    public TravelRouteRequest(String departure, String destination, String transportation) {
        this.departure = departure;
        this.destination = destination;
        this.transportation = transportation;
    }

    public TravelRoute toEntity(TravelRouteRequest request, TravelTimeAiService travelTimeAiService) {
        this.departure = request.departure;
        this.destination = request.destination;
        this.transportation = request.transportation;

        if (travelRouteNotExist(request)) {
            return new TravelRoute(departure, destination, transportation, null);
        }

        TravelTimeRequest aiRequest = new TravelTimeRequest(
            request.getDeparture(),
            request.getDestination(),
            request.getTransportation()
        );

        TravelTimeResponse aiResponse = travelTimeAiService.predictTime(aiRequest);
        this.expectedTime = aiResponse.getExpectedTime();

        return new TravelRoute(departure, destination, transportation, expectedTime);

    }

    private Boolean travelRouteNotExist(TravelRouteRequest request) {
        if (request.getDeparture() != null && !request.getDeparture().isBlank()) {
            return false;
        }
        if (request.getDestination() != null && !request.getDestination().isBlank()) {
            return false;
        }
        if (request.getTransportation() != null && !request.getTransportation().isBlank()) {
            return false;
        }
        return true;
    }
}
