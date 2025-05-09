package grepp.NBE5_6_2_Team03.global.exception;

public enum Message {
    USER_EMAIL_DUPLICATED("회원 이메일이 이미 사용중 입니다."),
    USER_NAME_DUPLICATED("회원 이름이 이미 사용중 입니다.");

    private final String description;

    Message(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
