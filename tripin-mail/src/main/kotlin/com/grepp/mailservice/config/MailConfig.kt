package com.grepp.mailservice.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.*

@Configuration
class MailConfig(@Value("\${spring.mail.host}") private val host: String,
                 @Value("\${spring.mail.username}")
                 private val username: String,
                 @Value("\${spring.mail.password}")
                 private val password: String
) {

    @Bean
    fun javaMailService(): JavaMailSender = JavaMailSenderImpl().apply {
        this.host = this@MailConfig.host
        this.username = this@MailConfig.username
        this.password = this@MailConfig.password
        this.port = 465
        this.javaMailProperties = mailProperties
    }

    private val mailProperties: Properties
        get() {
            val properties = Properties()
            properties.setProperty("mail.transport.protocol", "smtp")
            properties.setProperty("mail.smtp.auth", "true")
            properties.setProperty("mail.smtp.starttls.enable", "true")
            properties.setProperty("mail.debug", "true")
            properties.setProperty("mail.smtp.ssl.trust", "*")
            properties.setProperty("mail.smtp.ssl.enable", "true")
            return properties
        }
}
