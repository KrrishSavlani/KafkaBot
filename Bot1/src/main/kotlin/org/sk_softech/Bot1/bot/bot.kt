package org.sk_softech.Bot1.bot

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Component
@RestController
@RequestMapping("/api")
class bot : TelegramLongPollingBot()
{

    val grpId : String = "-1002404054343"
    @Value("\${telegram.bot.token}")  // ✅ Inject bot token from properties
    private lateinit var botToken: String

    @Value("\${telegram.bot.username}")  // ✅ Inject bot username from properties
    private lateinit var botUsername: String

    override fun getBotToken(): String {
        return botToken  // ✅ Correctly returning the bot token
    }

    override fun getBotUsername(): String {
        return botUsername
    }

    override fun onUpdateReceived(update: Update) {
        //TODO("Not yet implemented")
        if (update.hasMessage() && update.message.hasText() && update.message.chatId != null) {
            val message = update.message
            val chatId = message.chatId.toString()
            val user = message.from
            val fName = user.firstName
            val lName = user.lastName
            var text = message.text
            val id = user.id


        }
    }

    fun sendMessage(chatId: String, text: String): Serializable {
        val sendMessage = SendMessage()
        sendMessage.chatId = chatId
        sendMessage.text = text

        return try {
            execute(sendMessage) // If this fails, it throws
        } catch (e: TelegramApiRequestException) {
            println("❌ Telegram failed with: ${e.apiResponse}")
            throw e // <--- Let it throw, so we catch it in listener
        }
    }
}
