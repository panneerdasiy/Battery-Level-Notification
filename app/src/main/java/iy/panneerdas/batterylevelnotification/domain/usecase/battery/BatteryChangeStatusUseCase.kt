package iy.panneerdas.batterylevelnotification.domain.usecase.battery

import iy.panneerdas.batterylevelnotification.domain.model.BatteryStatus
import iy.panneerdas.batterylevelnotification.domain.platform.BatteryChangeStatusProvider
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

interface BatteryChangeStatusUseCase {
    operator fun invoke(): SharedFlow<BatteryStatus>
}

class BatteryChangeStatusUseCaseImpl @Inject constructor(
    private val provider: BatteryChangeStatusProvider
) : BatteryChangeStatusUseCase {

    override fun invoke() = provider.invoke()
}