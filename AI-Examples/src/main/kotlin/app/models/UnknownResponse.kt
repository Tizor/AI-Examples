package app.models

import com.fasterxml.jackson.annotation.JsonProperty



data class UnknownResponse (

    @JsonProperty("message")
     val message: String? = null, // Пояснение, что запрос не распознан

    @JsonProperty("suggested_categories")
     val suggestedCategories: String? = null // Подсказка, что можно спросить
)