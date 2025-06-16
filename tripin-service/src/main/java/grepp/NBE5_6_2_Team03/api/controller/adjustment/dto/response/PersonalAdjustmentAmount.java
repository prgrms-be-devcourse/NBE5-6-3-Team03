package grepp.NBE5_6_2_Team03.api.controller.adjustment.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PersonalAdjustmentAmount {

    private final int personalWonToReceive;
    private final int personalForeignToReceive;
    private final int personalWonToPay;
    private final int personalForeignToPay;

    @Builder
    public PersonalAdjustmentAmount(int personalWonToReceive, int personalForeignToReceive, int personalWonToPay, int personalForeignToPay) {
        this.personalWonToReceive = personalWonToReceive;
        this.personalForeignToReceive = personalForeignToReceive;
        this.personalWonToPay = personalWonToPay;
        this.personalForeignToPay = personalForeignToPay;
    }
}
