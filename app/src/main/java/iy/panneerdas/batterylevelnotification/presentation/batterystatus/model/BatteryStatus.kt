package iy.panneerdas.batterylevelnotification.presentation.batterystatus.model

data class DisplayBatteryStatus(
    val percent: Int, val chargingStatus: String
)