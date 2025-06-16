package com.grepp.mailservice.service.strategy

import org.springframework.stereotype.Component
import java.security.SecureRandom

@Component
class VerificationCodeProvider : CodeProvidable {

    companion object {
        private val RANDOM = SecureRandom()
        private const val CODE_LENGTH = 10
    }

    override fun isSupport(codeType: CodeType): Boolean = codeType == CodeType.VERIFICATION_CODE
    override fun provide(): String = (1..CODE_LENGTH)
        .map { RANDOM.nextInt(10) }
        .joinToString("")
}
