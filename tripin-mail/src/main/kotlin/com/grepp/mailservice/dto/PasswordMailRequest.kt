package com.grepp.mailservice.dto

data class PasswordMailRequest(
    val to: String,
    val subject: String,
    val text: String
)