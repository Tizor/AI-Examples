package app.models

import com.fasterxml.jackson.annotation.JsonProperty



data class ClassificationResponse (

    @JsonProperty("category")
     var category: String? = null, // weather, order, payment, support, unknown

    @JsonProperty("confidence")
     var confidence: Double? = null, // Уверенность модели в классификации (0.0-1.0)

    @JsonProperty("original_query")
     var originalQuery: String? = null
     )