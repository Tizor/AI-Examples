//package app
//
//import chat.giga.client.GigaChatClient
//import chat.giga.model.ModelName
//import chat.giga.model.completion.ChatMessage
//import chat.giga.model.completion.ChatMessageRole
//import chat.giga.model.completion.CompletionRequest
//import chat.giga.model.completion.CompletionResponse
//import org.springframework.ai.chat.client.ChatClient
//import org.springframework.stereotype.Service
//
//@Service
//class AiAssistantService(
//    private val chatClient: GigaChatClient? = null
//) {
//
//    fun handleRequest(userQuestion: String): CompletionResponse? {
////        val e = ChatClient.Builder
////        return chatClient.prompt()
////            .system(
////                """
////                Ты классификатор запросов. Верни JSON-объект с полем type.
////                Если запрос о погоде → type = "weather"
////                Если о заказе → type = "order"
////                Если непонятно → type = "unknown"
////
////                """.trimIndent()
////            )
////            .user(userQuestion)
////            .call()
////            .entity<ChatResponse?>(ChatResponse::class.java)
//        val e = """
//            Ты — агент, который возвращает ТОЛЬКО JSON.
//            Выбери одну из схем по ключевым словам запроса:
//
//            1. Если запрос про "температуру, дождь, ветер" → используй схему weather:
//               {"type": "weather", "city": "string", "temp": number, "condition": "string"}
//
//            2. Если про "заказ, товар, доставка" → схема order:
//               {"type": "order", "order_id": "string", "status": "string", "items": array}
//
//            Запрос пользователя: Привет
//            Ответ (только JSON, без пояснений):
//        """.trimIndent()
//        return chatClient?.completions(
//            CompletionRequest.builder()
//            .model(ModelName.GIGA_CHAT_2)
//            .message(
//                ChatMessage.builder()
//                .content(e)
//                .role(ChatMessageRole.USER)
//                .build())
//            .build())
//    }
//
//    fun handleRequest_v2(builder: ChatClient.Builder): String? {
////        chatClient.completions(
////            CompletionRequest.builder()
////                .model(ModelName.GIGA_CHAT_2)
////                .message(
////                    ChatMessage.builder()
////                        .content("")
////                        .role(ChatMessageRole.USER)
////                        .build())
////                .build())
//
//        val chatClient: ChatClient? = builder.build()
//        return chatClient?.prompt()?.system("""
//                        Определи категорию запроса. Ответь ТОЛЬКО одним словом:
//                        weather, order, payment, support, unknown
//                        """)?.user("")?.call()?.content(); // Получаем строку "weather", "order" и т.д.
//    }
//}