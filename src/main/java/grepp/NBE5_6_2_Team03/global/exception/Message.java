package grepp.NBE5_6_2_Team03.global.exception;

public enum Message {
    SIGNUP_DUPLICATE_ERROR("회원 이메일 혹은 이름이 중복 사용중 입니다."),
    USER_NOT_FOUND("회원을 찾지 못했습니다."),
    USER_NOT_MATCH_PASSWORD("회원의 비밀번호가 일치하지 않습니다.");

    private final String description;

    Message(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
