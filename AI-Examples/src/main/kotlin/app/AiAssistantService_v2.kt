package app

import app.models.ClassificationResponse
import app.models.OrderResponse
import app.models.PaymentResponse
import app.models.SupportResponse
import app.models.UnknownResponse
import app.models.WeatherResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.ai.chat.client.ChatClient
import org.springframework.stereotype.Service

@Service
open class AiAssistantService_v2 (
    private val chatClient2: ChatClient,
    private val objectMapper: ObjectMapper,
) {

    /**
     * Шаг 1: Классификация запроса
     * Определяет категорию запроса пользователя
     */
    fun classifyRequest(userQuestion: String?): ClassificationResponse? {
        log.debug("Classifying request: {}", userQuestion)

        val prompt: String = """
            Ты - классификатор запросов. Определи категорию вопроса пользователя.
            
            Доступные категории:
            - weather: вопросы о погоде, температуре, осадках, ветре
            - order: вопросы о заказах, доставке, статусе посылки
            - payment: вопросы о платежах, переводах, счетах, балансе
            - support: вопросы о техподдержке, проблемах с сервисом
            - unknown: если вопрос не подходит ни под одну категорию
            
            Правила:
            1. Верни ТОЛЬКО JSON в указанном формате
            2. Без пояснений, без markdown
            3. confidence должно быть от 0.0 до 1.0
            
            Формат ответа:
            {
                "category": "weather|order|payment|support|unknown",
                "confidence": 0.95
            }
            
            Вопрос пользователя: ${userQuestion}
            
            """.trimIndent()

        try {
            val jsonResponse = chatClient2!!.prompt()
                .user(prompt)
                .call()
                .content()

            log.debug("Classification response raw: {}", jsonResponse)


            // Парсим JSON с возможным текстом вокруг
            val cleanJson = extractJson(jsonResponse)
            var response: ClassificationResponse? =
                objectMapper?.readValue<ClassificationResponse>(cleanJson, ClassificationResponse::class.java)
            response?.originalQuery = userQuestion
//                .setOriginalQuery(userQuestion)

            return response
        } catch (e: Exception) {
            log.error("Failed to classify request: {}", e.message)
            // Возвращаем unknown в случае ошибки
            return ClassificationResponse("unknown", 0.0, userQuestion)
        }
    }

    /**
     * Шаг 2: Получение структурированного ответа на основе категории
     */
    fun handleRequest(userQuestion: String): Any? {
        // Шаг 1: Классифицируем
        val classification: ClassificationResponse? = classifyRequest(userQuestion)
        val category: String? = classification?.category
//            .getCategory()
        val confidence: Double? = classification?.confidence
//            .getConfidence()

        log.info("Request classified as: {} (confidence: {})", category, confidence)


        // Шаг 2: В зависимости от категории, запрашиваем соответствующую структуру
        return when (category) {
            "weather" -> getWeatherResponse(userQuestion)
            "order" -> getOrderResponse(userQuestion)
            "payment" -> getPaymentResponse(userQuestion)
            "support" -> getSupportResponse(userQuestion)
            else -> getUnknownResponse(userQuestion)
        }
    }

    /**
     * Запрос погодных данных
     */
    private fun getWeatherResponse(userQuestion: String): WeatherResponse? {
        val systemPrompt = """
            Ты - ассистент по погоде. Извлеки из вопроса пользователя данные о погоде.
            
            Верни ТОЛЬКО JSON в точном соответствии со схемой:
            {
                "city": "название города (строка)",
                "temperature_celsius": температура в градусах Цельсия (число),
                "condition": "погодное условие (солнечно/дождливо/облачно/снежно)",
                "humidity_percent": влажность в процентах (число от 0 до 100),
                "wind_speed_kph": скорость ветра в км/ч (число)
            }
            
            Если данных нет в вопросе, используй разумные значения по умолчанию.
            
            """.trimIndent()

        return chatClient2!!.prompt()
            .system(systemPrompt)
            .user(userQuestion)
            .call()
            .entity<WeatherResponse?>(WeatherResponse::class.java)
    }

    /**
     * Запрос информации о заказе
     */
    private fun getOrderResponse(userQuestion: String): OrderResponse? {
        val systemPrompt = """
            Ты - ассистент по заказам. Извлеки из вопроса пользователя данные о заказе.
            
            Верни ТОЛЬКО JSON в точном соответствии со схемой:
            {
                "order_id": "идентификатор заказа (строка)",
                "status": "статус (created/processing/shipped/delivered/cancelled)",
                "items": [
                    {
                        "name": "название товара",
                        "quantity": количество (число),
                        "price": цена (число)
                    }
                ],
                "total_amount": общая сумма (число),
                "estimated_delivery_date": "дата доставки в формате ГГГГ-ММ-ДД"
            }
            
            """.trimIndent()

        return chatClient2!!.prompt()
            .system(systemPrompt)
            .user(userQuestion)
            .call()
            .entity<OrderResponse?>(OrderResponse::class.java)
    }

    /**
     * Запрос платежной информации
     */
    private fun getPaymentResponse(userQuestion: String): PaymentResponse? {
        val systemPrompt = """
            Ты - платежный ассистент. Извлеки из вопроса пользователя данные о платеже.
            
            Верни ТОЛЬКО JSON в точном соответствии со схемой:
            {
                "transaction_id": "идентификатор транзакции (строка)",
                "amount": сумма (число),
                "currency": "валюта (RUB/USD/EUR)",
                "status": "статус (success/pending/failed)",
                "payment_method": "метод платежа (card/cash/transfer)"
            }
            
            """.trimIndent()

        return chatClient2!!.prompt()
            .system(systemPrompt)
            .user(userQuestion)
            .call()
            .entity<PaymentResponse?>(PaymentResponse::class.java)
    }

    /**
     * Запрос в службу поддержки
     */
    private fun getSupportResponse(userQuestion: String): SupportResponse? {
        val systemPrompt = """
            Ты - ассистент службы поддержки. Обработай вопрос пользователя.
            
            Верни ТОЛЬКО JSON в точном соответствии со схемой:
            {
                "ticket_id": "сгенерированный ID обращения (строка формата TICKET-XXXXX)",
                "issue_type": "тип проблемы (technical/billing/general)",
                "response": "текст ответа поддержки",
                "estimated_wait_minutes": примерное время ожидания в минутах (число)
            }
            
            """.trimIndent()

        return chatClient2!!.prompt()
            .system(systemPrompt)
            .user(userQuestion)
            .call()
            .entity<SupportResponse?>(SupportResponse::class.java)
    }

    /**
     * Ответ для неизвестной категории
     */
    private fun getUnknownResponse(userQuestion: String): UnknownResponse? {
        val systemPrompt = """
            Ты - ассистент. Запрос пользователя не распознан ни под одну категорию.
            
            Верни ТОЛЬКО JSON в формате:
            {
                "message": "пояснение, что запрос не распознан и почему",
                "suggested_categories": "подсказка, что можно спросить"
            }
            
            """.trimIndent()

        return chatClient2!!.prompt()
            .system(systemPrompt)
            .user(userQuestion)
            .call()
            .entity<UnknownResponse?>(UnknownResponse::class.java)
    }

    /**
     * Извлекает чистый JSON из ответа модели (убирает markdown и пояснения)
     */
    private fun extractJson(rawResponse: String?): String {
        if (rawResponse == null) return "{}"


        // Убираем markdown-блоки
        val cleaned = rawResponse.replace("```json\\s*".toRegex(), "")
            .replace("```\\s*".toRegex(), "")
            .trim { it <= ' ' }


        // Ищем первый { и последний }
        val start = cleaned.indexOf('{')
        val end = cleaned.lastIndexOf('}')

        if (start != -1 && end != -1 && start < end) {
            return cleaned.substring(start, end + 1)
        }

        return cleaned
    }

    companion object {
        private val log = LoggerFactory.getLogger(AiAssistantService_v2::class.java)
    }
}