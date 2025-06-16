package com.grepp.mailservice.service.strategy

interface CodeProvidable {
    fun isSupport(codeType: CodeType): Boolean
    fun provide(): String
}
