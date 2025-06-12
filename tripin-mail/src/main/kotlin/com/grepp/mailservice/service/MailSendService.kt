package com.grepp.mailservice.service

import com.grepp.mailservice.dto.PasswordMailRequest
import com.grepp.mailservice.dto.SettlementMailRequest
import jakarta.mail.internet.MimeMessage
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@Service
class MailSendService(
    private val mailSender: JavaMailSender,
    private val templateEngine: TemplateEngine
    ) {

    fun sendPasswordMail(request: PasswordMailRequest) {
        val message = SimpleMailMessage()
        message.setTo(request.to)
        message.subject = request.subject
        message.text = request.text
        mailSender.send(message)
    }

    fun sendSettlementMail(request: SettlementMailRequest) {
        val mimeMessage: MimeMessage = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(mimeMessage, true, "UTF-8")

        val context = Context().apply {
            setVariables(request.templateModel)
        }

        val html = templateEngine.process("mail/${request.templateName}", context)

        helper.setTo(request.to)
        helper.setSubject(request.subject)
        helper.setText(html,true)

        mailSender.send(mimeMessage)
    }
}