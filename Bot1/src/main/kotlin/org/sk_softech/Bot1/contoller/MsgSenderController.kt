package org.sk_softech.Bot1.controller

import org.sk_softech.Bot1.bot.bot
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/msg")
class MsgSenderController(private val bot : bot,
    private val kafkaTemplate: KafkaTemplate<String, String>
) {

    @PostMapping("/{number}/send")
    fun sendMessage(@PathVariable number: String): String {
        val currentTime = LocalDateTime.now()
        val formattedTime = currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

        val message = "$number"
        kafkaTemplate.send("sender", message)

       // bot.sendMessage(bot.grpId ,  "Message sent to Kafka for number: $number at $formattedTime")
        return message
    }
}
