package org.sk_softech.Bot1.contoller

import org.sk_softech.Bot1.bot.bot
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicInteger

@Component
class TelegramKafkaListener(private val bot: bot) {

    private val messageBatch = CopyOnWriteArrayList<String>()
    private val batchCounter = AtomicInteger(0)
    private val batchSize = 15

    @KafkaListener(
        topics = ["sender"],
        groupId = "telegramMessage",
        containerFactory = "kafkaListenerContainerFactory"
    )
    fun onBatchMessage(messages: List<String>, ack: Acknowledgment) {
        println("📥 Batch Received: ${messages.size}")

        var i = 0
        while (i < messages.size) {

            val msg = messages[i]
            try {
                println("🧾 Sending message: $msg")
                val response = bot.sendMessage(bot.grpId, msg)
                println("✅ Response: $response")

                // Only increment if the response is OK
                if (response != null) {
                    i++
                    println("i++ is working")
                } else {
                    println("❗ Message not sent successfully, will retry...")
                    Thread.sleep(5000)
                }
            } catch (e: Exception) {
                println("⚠️ Error sending message: ${e.message}")
                println("⏳ Waiting 40 seconds before retrying message...")
                Thread.sleep(5000)
            }

            println("✅ All messages sent successfully. Acknowledging offset.")
            ack.acknowledge()
        }
    }
}