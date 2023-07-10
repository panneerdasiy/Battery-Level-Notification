package iy.panneerdas.batterylevelnotification.domain.usecase

import iy.panneerdas.batterylevelnotification.domain.model.BatteryStatus
import javax.inject.Inject

interface SmartStartAndStopChargeAlertUseCase {

    operator fun invoke(batteryStatus: BatteryStatus)
}

class SmartStartAndStopChargeAlertUseCaseImpl @Inject constructor(
    private val startAlertUseCase: SmartChargeStartAlertUseCase,
    private val stopAlertUseCase: SmartChargeStopAlertUseCase,
) : SmartStartAndStopChargeAlertUseCase {

    override fun invoke(batteryStatus: BatteryStatus) {
        startAlertUseCase(batteryStatus = batteryStatus)
        stopAlertUseCase(batteryStatus = batteryStatus)
    }
}