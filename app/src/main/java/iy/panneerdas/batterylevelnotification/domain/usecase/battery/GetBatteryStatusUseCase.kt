package iy.panneerdas.batterylevelnotification.domain.usecase.battery

import iy.panneerdas.batterylevelnotification.domain.model.BatteryStatus
import iy.panneerdas.batterylevelnotification.domain.platform.BatteryStatusProvider
import javax.inject.Inject

interface GetBatteryStatusUseCase {
    operator fun invoke(): BatteryStatus?
}

class GetBatteryStatusUseCaseImpl @Inject constructor(
    private val batteryStatusProvider: BatteryStatusProvider,
) :
    GetBatteryStatusUseCase {
    override fun invoke(): BatteryStatus? = batteryStatusProvider()
}