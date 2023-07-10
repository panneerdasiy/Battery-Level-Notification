package iy.panneerdas.batterylevelnotification.domain.usecase.alertservice

import iy.panneerdas.batterylevelnotification.di.Dispatcher
import iy.panneerdas.batterylevelnotification.di.DispatcherType
import iy.panneerdas.batterylevelnotification.di.StartAlertService
import iy.panneerdas.batterylevelnotification.domain.usecase.alertsetting.GetObservableBatteryAlertSettingUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

interface StartSmartChargeServiceIfAllowedUseCase {
    operator fun invoke()
}

class StartSmartChargeServiceIfAllowedUseCaseImpl @Inject constructor(
    private val getObservableBatteryAlertSettingUseCase: GetObservableBatteryAlertSettingUseCase,
    @StartAlertService private val startBatteryAlertServiceUseCase: StartBatteryAlertServiceUseCase,
    @Dispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : StartSmartChargeServiceIfAllowedUseCase {

    override fun invoke() {
        CoroutineScope(dispatcher).launch {
            val isSmartChargingAlertEnabled = getObservableBatteryAlertSettingUseCase().first()
            if (isSmartChargingAlertEnabled)
                startBatteryAlertServiceUseCase.start()
        }
    }

}