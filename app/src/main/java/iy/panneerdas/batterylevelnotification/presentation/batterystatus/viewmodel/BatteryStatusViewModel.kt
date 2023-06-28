package iy.panneerdas.batterylevelnotification.presentation.batterystatus.viewmodel

import iy.panneerdas.batterylevelnotification.R
import iy.panneerdas.batterylevelnotification.domain.model.BatteryChargingStatus
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryAlertSettingUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.GetObservableBatteryChangeStatusUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryMonitorWorkerUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.worker.GetAllWorkerLogUseCase
import iy.panneerdas.batterylevelnotification.platform.I18nStringProvider
import iy.panneerdas.batterylevelnotification.platform.LifeCycleCoroutineScopeProvider
import iy.panneerdas.batterylevelnotification.presentation.batterystatus.model.DisplayBatteryStatus
import iy.panneerdas.batterylevelnotification.presentation.batterystatus.model.DisplayWorkerLog
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class BatteryStatusViewModel @Inject constructor(
    private val i18nStringProvider: I18nStringProvider,
    private val batteryMonitorWorkerUseCase: BatteryMonitorWorkerUseCase,
    private val batteryAlertSettingUseCase: BatteryAlertSettingUseCase,
    lifecycleCoroutineProvider: LifeCycleCoroutineScopeProvider,
    getObservableBatteryChangeStatusUseCase: GetObservableBatteryChangeStatusUseCase,
    getAllWorkerLogUseCase: GetAllWorkerLogUseCase,
) {
    private val viewModelScope = lifecycleCoroutineProvider.coroutineScope()

    val requestPermissionState = MutableSharedFlow<Unit>()
    val batteryStatus = getObservableBatteryChangeStatusUseCase().map {
        DisplayBatteryStatus(
            percent = it.percent.toInt(),
            chargingStatus = getDisplayChargingStatus(it.chargingStatus)
        )
    }

    private fun getDisplayChargingStatus(chargingStatus: BatteryChargingStatus): String {
        return when (chargingStatus) {
            BatteryChargingStatus.CHARGING -> i18nStringProvider.getString(R.string.charging)
            BatteryChargingStatus.NOT_CHARGING -> i18nStringProvider.getString(R.string.not_charging)
            BatteryChargingStatus.UNKNOWN -> i18nStringProvider.getString(R.string.unknown)
        }
    }

    val isAlertEnabledFlow = batteryAlertSettingUseCase.getAlertEnableStatus()

    val logsFlow = getAllWorkerLogUseCase()
        .map { logs ->
            logs.map { log ->
                val formatter = SimpleDateFormat("dd/MM/yy hh:mm a", Locale.getDefault())
                DisplayWorkerLog(
                    id = log.id,
                    dateTime = formatter.format(log.date),
                    batteryPercent = log.batteryPercent
                )
            }
        }

    fun onAlertToggleChange(isChecked: Boolean) {
        viewModelScope.launch {
            batteryAlertSettingUseCase.setAlertEnableStatus(enable = isChecked)

            if (isChecked) {
                onAlertToggleEnable()
                return@launch
            }

            disableAlert()
        }
    }

    private fun onAlertToggleEnable() {
        viewModelScope.launch {
            requestPermissionState.emit(Unit)
        }
    }

    fun onPermissionResult(granted: Boolean) {
        if (granted) {
            enableAlert()
            return
        }

        disableAlert()
    }

    private fun enableAlert() {
        viewModelScope.launch {
            batteryMonitorWorkerUseCase.scheduleWork()
            batteryAlertSettingUseCase.setAlertEnableStatus(enable = true)
        }
    }

    private fun disableAlert() {
        viewModelScope.launch {
            batteryMonitorWorkerUseCase.cancelWork()
            batteryAlertSettingUseCase.setAlertEnableStatus(enable = false)
        }
    }
}