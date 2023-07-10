package iy.panneerdas.batterylevelnotification.domain.usecase

import iy.panneerdas.batterylevelnotification.di.Dispatcher
import iy.panneerdas.batterylevelnotification.di.DispatcherType
import iy.panneerdas.batterylevelnotification.domain.model.BatteryChargingStatus
import iy.panneerdas.batterylevelnotification.domain.model.BatteryStatus
import iy.panneerdas.batterylevelnotification.domain.platform.BatteryAlertHandler
import iy.panneerdas.batterylevelnotification.domain.usecase.alertsetting.GetStartChargeAlertEnableStatusUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.alertsetting.SetStartChargeAlertEnableStatusUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

interface SmartChargeStartAlertUseCase {
    operator fun invoke(batteryStatus: BatteryStatus)
}

class SmartChargeStartAlertUseCaseImpl @Inject constructor(
    private val handler: BatteryAlertHandler,
    private val setStartChargeAlertEnableStatusUseCase: SetStartChargeAlertEnableStatusUseCase,
    private val getStartChargeAlertEnableStatusUseCase: GetStartChargeAlertEnableStatusUseCase,
    @Dispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : SmartChargeStartAlertUseCase {

    override fun invoke(batteryStatus: BatteryStatus) {
        CoroutineScope(dispatcher).launch {

            if (shouldStartCharging(batteryStatus)) {
                handler.startCharging(batteryStatus)
                disableStartChargingAlert()
                return@launch
            }

            if (shouldEnableStartChargeAlert(batteryStatus)) {
                enableStartChargingAlert()
                return@launch
            }

        }
    }

    private suspend fun shouldStartCharging(batteryStatus: BatteryStatus): Boolean {
        return batteryStatus.percent <= 30
                && batteryStatus.chargingStatus == BatteryChargingStatus.NOT_CHARGING
                && !isStartChargingAlertDisabled()
    }

    private suspend fun isStartChargingAlertDisabled(): Boolean {
        return getStartChargeAlertEnableStatusUseCase()
    }

    private suspend fun disableStartChargingAlert() {
        setStartChargeAlertEnableStatusUseCase(false)
    }

    private fun shouldEnableStartChargeAlert(batteryStatus: BatteryStatus): Boolean {
        return batteryStatus.chargingStatus == BatteryChargingStatus.CHARGING
    }

    private suspend fun enableStartChargingAlert() {
        setStartChargeAlertEnableStatusUseCase(true)
    }
}