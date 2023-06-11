package iy.panneerdas.batterylevelnotification.domain.model

data class BatteryStatus(
    val percent: Float = -1f,
    val chargingStatus: BatteryChargingStatus = BatteryChargingStatus.UNKNOWN
)