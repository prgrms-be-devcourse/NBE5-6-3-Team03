package com.grepp.mailservice.service

import com.grepp.mailservice.dto.CodeMailRequest
import com.grepp.mailservice.dto.HtmlMailRequest
import com.grepp.mailservice.service.strategy.CodeFinder
import com.grepp.mailservice.service.strategy.CodeType
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
    private val templateEngine: TemplateEngine,
    private val codeFinder: CodeFinder
    ) {

    fun sendCodeMail(to: String, codeType: CodeType): String {
        val code = codeFinder.findCodeFrom(codeType)
        val message = SimpleMailMessage().apply {
            setTo(to)
            subject = (if(codeType == CodeType.PASSWORD) "임시 비밀번호 안내" else "임시 코드 안내").toString()
            text = "생성된 코드는 [$code] 입니다."
        }
        mailSender.send(message)
        return code
    }

    fun sendHtmlMail(request: HtmlMailRequest) {
        val mimeMessage: MimeMessage = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(mimeMessage, true, "UTF-8")
        val context = Context().apply { setVariables(request.templateModel)}
        val html = templateEngine.process("mail/${request.templateName}", context)

        helper.setTo(request.to)
        helper.setSubject(request.subject)
        helper.setText(html,true)
        mailSender.send(mimeMessage)
    }
}