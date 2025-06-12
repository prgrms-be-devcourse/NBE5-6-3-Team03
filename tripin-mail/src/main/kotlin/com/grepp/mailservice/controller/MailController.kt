package com.grepp.mailservice.controller

import com.grepp.mailservice.dto.PasswordMailRequest
import com.grepp.mailservice.dto.SettlementMailRequest
import com.grepp.mailservice.service.MailSendService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/mail")
class MailController(private val mailSendService: MailSendService) {

    @PostMapping("/send-password")
    fun sendSimpleMail(@RequestBody request: PasswordMailRequest): ResponseEntity<Unit> {
        mailSendService.sendPasswordMail(request)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/send-settlement")
    fun sendSettlementMail(@RequestBody request: SettlementMailRequest): ResponseEntity<Unit> {
        mailSendService.sendSettlementMail(request)
        return ResponseEntity.ok().build()
    }

}