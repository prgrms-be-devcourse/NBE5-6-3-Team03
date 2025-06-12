package com.grepp.mailservice.dto

data class SettlementMailRequest(
    val to: String,
    val subject: String,
    val templateName: String,
    val templateModel: Map<String, Any>
)
