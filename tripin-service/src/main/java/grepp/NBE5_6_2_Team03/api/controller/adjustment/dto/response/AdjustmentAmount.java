package grepp.NBE5_6_2_Team03.api.controller.adjustment.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AdjustmentAmount {

    private final int wonToReceive;
    private final int foreignToReceive;
    private final int wonToPay;
    private final int foreignToPay;

    @Builder
    public AdjustmentAmount(int wonToReceive, int foreignToReceive, int wonToPay, int foreignToPay) {
        this.wonToReceive = wonToReceive;
        this.foreignToReceive = foreignToReceive;
        this.wonToPay = wonToPay;
        this.foreignToPay = foreignToPay;
    }
}
