package grepp.NBE5_6_2_Team03.global.exception;

public class DuplicatedException extends BusinessException{
    private final Reason reason;

    public DuplicatedException(Reason reason, Message message) {
        super(message.getDescription());
        this.reason = reason;
    }

    public String getReason() {
        return reason.getDescription();
    }
}
