package iy.panneerdas.batterylevelnotification.domain.usecase

import iy.panneerdas.batterylevelnotification.di.Dispatcher
import iy.panneerdas.batterylevelnotification.di.DispatcherType
import iy.panneerdas.batterylevelnotification.domain.model.BatteryChargingStatus
import iy.panneerdas.batterylevelnotification.domain.model.BatteryStatus
import iy.panneerdas.batterylevelnotification.domain.platform.BatteryAlertHandler
import iy.panneerdas.batterylevelnotification.domain.usecase.alertsetting.GetStopChargeAlertEnableStatusUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.alertsetting.SetStopChargeAlertEnableStatusUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

interface SmartChargeStopAlertUseCase {
    operator fun invoke(batteryStatus: BatteryStatus)
}

class SmartChargeStopAlertUseCaseImpl @Inject constructor(
    private val handler: BatteryAlertHandler,
    private val setStopChargeAlertEnableStatusUseCase: SetStopChargeAlertEnableStatusUseCase,
    private val getStopChargeAlertEnableStatusUseCase: GetStopChargeAlertEnableStatusUseCase,
    @Dispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : SmartChargeStopAlertUseCase {

    override fun invoke(batteryStatus: BatteryStatus) {
        CoroutineScope(dispatcher).launch {

            if (shouldStopCharging(batteryStatus)) {
                handler.stopCharging(batteryStatus)
                disableStopChargingAlert()
                return@launch
            }

            if (isBatteryNotCharging(batteryStatus)) {
                handler.dismissStopCharging()
                enableStopChargingAlert()
                return@launch
            }
        }
    }

    private suspend fun shouldStopCharging(batteryStatus: BatteryStatus): Boolean {
        return batteryStatus.percent >= 80
                && batteryStatus.chargingStatus == BatteryChargingStatus.CHARGING
                && isStopChargingAlertEnabled()
    }

    private suspend fun isStopChargingAlertEnabled(): Boolean {
        return getStopChargeAlertEnableStatusUseCase()
    }

    private suspend fun disableStopChargingAlert() {
        setStopChargeAlertEnableStatusUseCase(false)
    }

    private fun isBatteryNotCharging(batteryStatus: BatteryStatus): Boolean {
        return batteryStatus.chargingStatus == BatteryChargingStatus.NOT_CHARGING
    }

    private suspend fun enableStopChargingAlert() {
        setStopChargeAlertEnableStatusUseCase(true)
    }
}