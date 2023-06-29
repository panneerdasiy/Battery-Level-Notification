package iy.panneerdas.batterylevelnotification.domain.usecase.status

import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import iy.panneerdas.batterylevelnotification.domain.model.BatteryStatus
import iy.panneerdas.batterylevelnotification.domain.platform.BatteryChangeStatusProvider
import kotlinx.coroutines.flow.SharedFlow

interface GetObservableBatteryChangeStatusUseCase {
    operator fun invoke(): SharedFlow<BatteryStatus>
}

class GetObservableBatteryChangeStatusUseCaseImpl @AssistedInject constructor(
    @Assisted private val provider: BatteryChangeStatusProvider
) : GetObservableBatteryChangeStatusUseCase {

    override fun invoke() = provider.invoke()
}