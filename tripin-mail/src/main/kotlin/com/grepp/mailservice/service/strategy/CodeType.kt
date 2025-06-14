package com.grepp.mailservice.service.strategy

enum class CodeType(val description: String) {
    VERIFICATION_CODE("인증 번호 발급"),
    PASSWORD("임시 비밀번호 발급");

    val isVerificationCode: Boolean
        get() = this == VERIFICATION_CODE

    val isNotVerificationCode: Boolean
        get() = !isVerificationCode

    val isPasswordCode: Boolean
        get() = this == PASSWORD

    val isNotPasswordCode: Boolean
        get() = !isPasswordCode
}
