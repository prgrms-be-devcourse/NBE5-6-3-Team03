package grepp.NBE5_6_2_Team03.domain.user;

public enum Role {
    ROLE_USER("기본 회원"),
    ROLE_ADMIN("관리자");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
