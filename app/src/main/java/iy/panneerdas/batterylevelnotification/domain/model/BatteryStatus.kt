package iy.panneerdas.batterylevelnotification.domain.model

data class BatteryStatus(val percent: Float, val chargingStatus: BatteryChargingStatus)