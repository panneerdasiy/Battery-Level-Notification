package iy.panneerdas.batterylevelnotification.domain.usecase.battery

import iy.panneerdas.batterylevelnotification.domain.model.BatteryStatus
import iy.panneerdas.batterylevelnotification.domain.platform.BatteryStatusProvider
import javax.inject.Inject

interface BatteryStatusUseCase {
    operator fun invoke(): BatteryStatus?
}

class BatteryStatusUseCaseImpl @Inject constructor(
    private val batteryStatusProvider: BatteryStatusProvider,
) :
    BatteryStatusUseCase {
    override fun invoke(): BatteryStatus? = batteryStatusProvider()
}