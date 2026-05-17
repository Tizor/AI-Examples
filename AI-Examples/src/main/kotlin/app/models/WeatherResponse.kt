package app.models

import com.fasterxml.jackson.annotation.JsonProperty



data class WeatherResponse (

    @JsonProperty("city")
     val city: String? = null,

    @JsonProperty("temperature_celsius")
     val temperature: Double? = null,

    @JsonProperty("condition")
     val condition: String? = null, // "солнечно", "дождливо", "облачно", "снежно"

    @JsonProperty("humidity_percent")
     val humidity: Int? = null,

    @JsonProperty("wind_speed_kph")
     val windSpeed: Double? = null
)