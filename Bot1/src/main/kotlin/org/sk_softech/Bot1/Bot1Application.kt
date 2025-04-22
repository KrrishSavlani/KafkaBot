package org.sk_softech.Bot1

import org.sk_softech.Bot1.bot.bot
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@EnableScheduling
@SpringBootApplication
class Bot1Application() {

    @Bean
    fun run( bot: bot): CommandLineRunner {
        return CommandLineRunner {
            try {
                val botsApi = TelegramBotsApi(DefaultBotSession::class.java)
                botsApi.registerBot(bot)  // ✅ Correctly registering the bot instance
                println("Bot registered successfully!")
            } catch (e: TelegramApiException) {
                e.printStackTrace()
            }
        }
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(Bot1Application::class.java, *args)
}
