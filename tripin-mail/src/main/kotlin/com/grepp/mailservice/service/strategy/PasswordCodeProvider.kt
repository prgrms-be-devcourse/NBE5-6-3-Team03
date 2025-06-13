package com.grepp.mailservice.service.strategy

import org.springframework.stereotype.Component
import java.security.SecureRandom

@Component
class PasswordCodeProvider : CodeProvidable {

    companion object {
        private const val CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        private const val PASSWORD_LENGTH = 10
        private val RANDOM = SecureRandom()
    }

    override fun isSupport(codeType: CodeType): Boolean = codeType == CodeType.PASSWORD

    override fun provide(): String = (1..PASSWORD_LENGTH)
        .map{ CHARACTERS[RANDOM.nextInt(CHARACTERS.length)]}
        .joinToString("")

}
