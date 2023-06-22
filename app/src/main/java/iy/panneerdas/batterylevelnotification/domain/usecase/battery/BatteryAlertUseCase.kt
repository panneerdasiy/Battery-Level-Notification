package iy.panneerdas.batterylevelnotification.domain.usecase.battery

import iy.panneerdas.batterylevelnotification.domain.model.BatteryChargingStatus
import iy.panneerdas.batterylevelnotification.domain.model.BatteryStatus
import iy.panneerdas.batterylevelnotification.domain.platform.BatteryAlertHandler
import javax.inject.Inject

interface BatteryAlertUseCase {

    operator fun invoke(batteryStatus: BatteryStatus)
}

class BatteryAlertUseCaseImpl @Inject constructor(
    private val handler: BatteryAlertHandler
) : BatteryAlertUseCase {

    override fun invoke(batteryStatus: BatteryStatus) {
        if (shouldStopCharging(batteryStatus)) {
            handler.stopCharging(batteryStatus)
            return
        }

        if (shouldStartCharging(batteryStatus)) {
            handler.startCharging(batteryStatus)
            return
        }
    }

    private fun shouldStopCharging(batteryStatus: BatteryStatus): Boolean {
        return batteryStatus.percent >= 80
                && batteryStatus.chargingStatus == BatteryChargingStatus.CHARGING
    }

    private fun shouldStartCharging(batteryStatus: BatteryStatus): Boolean {
        return batteryStatus.percent <= 30
                && batteryStatus.chargingStatus == BatteryChargingStatus.NOT_CHARGING
    }
}