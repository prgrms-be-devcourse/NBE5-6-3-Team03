package grepp.NBE5_6_2_Team03.global.exception;

public enum Reason {
    SIGN_UP("회원 가입");

    private final String description;

    Reason(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
