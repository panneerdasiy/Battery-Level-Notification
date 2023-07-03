package iy.panneerdas.batterylevelnotification.domain.usecase.alertservice

import iy.panneerdas.batterylevelnotification.di.StartMonitorService
import iy.panneerdas.batterylevelnotification.domain.usecase.alertsetting.GetObservableBatteryAlertSettingUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

interface RestartHandleBatteryAlertServiceUseCase {
    operator fun invoke()
}

class RestartHandleBatteryAlertServiceUseCaseImpl @Inject constructor(
    private val getObservableBatteryAlertSettingUseCase: GetObservableBatteryAlertSettingUseCase,
    @StartMonitorService private val startBatteryAlertServiceUseCase: StartBatteryAlertServiceUseCase
) : RestartHandleBatteryAlertServiceUseCase {

    override fun invoke() {
        CoroutineScope(Dispatchers.IO).launch {
            val isSmartChargingAlertEnabled = getObservableBatteryAlertSettingUseCase().first()
            if (isSmartChargingAlertEnabled)
                startBatteryAlertServiceUseCase.start()
        }
    }

}