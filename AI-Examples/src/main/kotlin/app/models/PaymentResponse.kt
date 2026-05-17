package app.models

import com.fasterxml.jackson.annotation.JsonProperty



data class PaymentResponse (

    @JsonProperty("transaction_id")
     val transactionId: String? = null,

    @JsonProperty("amount")
     val amount: Double? = null,

    @JsonProperty("currency")
     val currency: String? = null, // "RUB", "USD", "EUR"

    @JsonProperty("status")
     val status: String? = null, // "success", "pending", "failed"

    @JsonProperty("payment_method")
     val paymentMethod: String? = null // "card", "cash", "transfer"
)