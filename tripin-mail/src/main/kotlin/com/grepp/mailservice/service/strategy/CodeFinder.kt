package com.grepp.mailservice.service.strategy

import org.springframework.stereotype.Component
import java.util.function.Supplier

@Component
class CodeFinder(private val providers: List<CodeProvidable>) {
    fun findCodeFrom(codeType: CodeType): String {
        return providers.find { it.isSupport(codeType) }
            ?.provide()
            ?: throw IllegalArgumentException("지원하지 않는 코드 타입입니다: $codeType")
    }
}
