package iy.panneerdas.batterylevelnotification.domain.usecase.alertservice

import iy.panneerdas.batterylevelnotification.di.StartAlertService
import iy.panneerdas.batterylevelnotification.domain.usecase.alertsetting.GetObservableBatteryAlertSettingUseCase
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
    @StartAlertService private val startBatteryAlertServiceUseCase: StartBatteryAlertServiceUseCase
) : StartSmartChargeServiceIfAllowedUseCase {

    override fun invoke() {
        CoroutineScope(Dispatchers.IO).launch {
            val isSmartChargingAlertEnabled = getObservableBatteryAlertSettingUseCase().first()
            if (isSmartChargingAlertEnabled)
                startBatteryAlertServiceUseCase.start()
        }
    }

}