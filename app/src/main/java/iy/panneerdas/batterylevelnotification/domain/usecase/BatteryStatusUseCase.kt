package iy.panneerdas.batterylevelnotification.domain.usecase

import iy.panneerdas.batterylevelnotification.domain.model.BatteryStatus
import iy.panneerdas.batterylevelnotification.platform.BatteryStatusProvider

interface BatteryStatusUseCase {
    operator fun invoke(): BatteryStatus?
}

class BatteryStatusUseCaseImpl(
    private val batteryStatusProvider: BatteryStatusProvider,
) :
    BatteryStatusUseCase {
    override fun invoke(): BatteryStatus? = batteryStatusProvider()
}