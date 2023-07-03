package iy.panneerdas.batterylevelnotification.domain.platform

import iy.panneerdas.batterylevelnotification.domain.model.BatteryStatus

interface BatteryStatusProvider {
    fun getStatus(): BatteryStatus?
}