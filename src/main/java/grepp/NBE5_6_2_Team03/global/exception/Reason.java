package grepp.NBE5_6_2_Team03.global.exception;

public enum Reason {
    USER_EMAIL("회원 이메일"),
    USER_NAME("회원 이름");

    private final String description;

    Reason(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
