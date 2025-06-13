package grepp.NBE5_6_2_Team03.global.exception;

public enum Message {
    SIGNUP_DUPLICATE_ERROR("회원 이메일 혹은 이름이 중복 사용중 입니다."),
    USER_NOT_FOUND("회원을 찾지 못했습니다."),
    USER_NOT_MATCH_PASSWORD("회원의 비밀번호가 일치하지 않습니다."),
    ADMIN_NOT_MODIFIED("관리자 계정은 변경할 수 없습니다."),
    PLANNED_NOT_FOUND("해당 여행 계획이 존재하지 않습니다."),
    PLACE_NOT_FOUND("해당 장소가 존재하지 않습니다."),
    SCHEDULE_NOT_FOUND("해당 일정이 존재하지 않습니다."),
    EXPENSE_NOT_FOUND("해당 지출이 존재하지 않습니다."),
    ALREADY_UNLOCKED("해당 유저는 이미 잠금해제되었습니다."),
    ALREADY_LOCKED("해당 유저는 이미 잠금처리되었습니다.");

    private final String description;

    Message(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}