package com.efrivahmi.elaborate.utils

import java.util.Properties
import javax.mail.Message
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

object EmailUtil {
    private const val SMTP_HOST = "your-smtp-host"
    private const val SMTP_PORT = 25
    private const val SENDER_EMAIL = "sender-email@example.com"

    fun sendVerificationCode(email: String, verificationCode: String, username: String) {
        val subject = "Verification Code"
        val content = "Hello $username,\n\nYour verification code is: $verificationCode\n\nPlease use this code to reset your password.\n\nRegards,\nThe App Team"

        val properties = Properties()
        properties["mail.smtp.host"] = SMTP_HOST
        properties["mail.smtp.port"] = SMTP_PORT

        val session = Session.getInstance(properties)

        try {
            val message = MimeMessage(session)
            message.setFrom(InternetAddress(SENDER_EMAIL))
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email))
            message.subject = subject
            message.setText(content)

            Transport.send(message)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}