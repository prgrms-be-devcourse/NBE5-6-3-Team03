package com.grepp.mailservice.dto

import com.grepp.mailservice.service.strategy.CodeType

data class CodeMailRequest(
    val to: String,
    val codeType: CodeType
)