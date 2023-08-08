package iy.panneerdas.batterylevelnotification.domain.platform

import iy.panneerdas.batterylevelnotification.domain.model.BatteryStatus

interface BatteryAlertHandler {

    fun startCharging(status: BatteryStatus)

    fun stopCharging(status: BatteryStatus)

    fun dismissStartCharging()

    fun dismissStopCharging()
}