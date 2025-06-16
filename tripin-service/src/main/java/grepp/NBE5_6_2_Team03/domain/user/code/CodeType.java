package grepp.NBE5_6_2_Team03.domain.user.code;

import lombok.Getter;

@Getter
public enum CodeType {
    VERIFICATION_CODE("인증 번호 발급"),
    PASSWORD("임시 비밀번호 발급");

    final String description;

    CodeType(String description) {
        this.description = description;
    }

    public boolean isVerificationCode() {
        return this == VERIFICATION_CODE;
    }

    public boolean isNotVerificationCode() {
        return !isVerificationCode();
    }

    public boolean isPasswordCode() {
        return this == PASSWORD;
    }

    public boolean isNotPasswordCode() {
        return !isPasswordCode();
    }
}
