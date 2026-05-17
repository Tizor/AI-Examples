package app.models

import com.fasterxml.jackson.annotation.JsonProperty



data class OrderResponse (

    @JsonProperty("order_id")
     val orderId: String? = null,

    @JsonProperty("status")
     val status: String? = null, // "created", "processing", "shipped", "delivered", "cancelled"

    @JsonProperty("items")
     val items: MutableList<OrderItem?>? = null,

    @JsonProperty("total_amount")
     val totalAmount: Double? = null,

    @JsonProperty("estimated_delivery_date")
     val estimatedDeliveryDate: String? = null
        ) {

    open class OrderItem {
         val name: String? = null
         val quantity: Int? = null
         val price: Double? = null
    }
}
