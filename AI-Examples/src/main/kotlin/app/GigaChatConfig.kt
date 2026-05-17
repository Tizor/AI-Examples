package app

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.module.kotlin.kotlinModule
import org.springframework.ai.chat.client.ChatClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary


@Configuration
open class GigaChatConfig {

    @Bean
    open fun chatClient2(builder: ChatClient.Builder): ChatClient {
        return builder
            .build()
    }

    @Bean
    @Primary
    open fun objectMapper(): ObjectMapper {
        val mapper = ObjectMapper()
        // Настройка для работы со snake_case в JSON (если нужно)
        mapper.propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
        mapper.registerModule(kotlinModule())

//            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
        return mapper
    }
}