package com.example.iotapps.model

enum class Screen {
    LOGIN,
    DASHBOARD,
    LIST_SENSOR
}

data class SensorItem (
    val id: String,
    val name: String,
    val type: String,
    val value: String,
    val unit: String,
    val status: String = "Normal" // Nilai default
)

object SensorCatalog {
    val availableSensors = listOf(
        SensorItem(
            id = "SR01",
            name = "Sensor Suhu",
            type = "DHT22",
            value = "65",
            unit = "°C"
        ),
        SensorItem(
            id = "SR02",
            name = "Sensor Gas",
            type = "MQ-2",
            value = "120",
            unit = "ppm"
        ),
        SensorItem(
            id = "SR03",
            name = "Sensor Cahaya",
            type = "B1750",
            value = "450",
            unit = "Lux"
        ),
    )
}