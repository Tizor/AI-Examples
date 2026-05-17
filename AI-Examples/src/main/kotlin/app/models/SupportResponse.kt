package app.models

import com.fasterxml.jackson.annotation.JsonProperty



data class SupportResponse (

    @JsonProperty("ticket_id")
     val ticketId: String? = null,

    @JsonProperty("issue_type")
     val issueType: String? = null, // "technical", "billing", "general"

    @JsonProperty("response")
     val response: String? = null, // Текстовый ответ поддержки

    @JsonProperty("estimated_wait_minutes")
     val estimatedWaitMinutes: Int? = null
)