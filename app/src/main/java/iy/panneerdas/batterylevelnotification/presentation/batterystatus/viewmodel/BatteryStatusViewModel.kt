package iy.panneerdas.batterylevelnotification.presentation.batterystatus.viewmodel

import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryAlertSettingUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryChangeStatusUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryMonitorWorkerUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.worker.WorkerLogUseCase
import iy.panneerdas.batterylevelnotification.platform.LifeCycleCoroutineScopeProvider
import iy.panneerdas.batterylevelnotification.presentation.batterystatus.model.WorkerLog
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Locale

class BatteryStatusViewModel(
    lifecycleCoroutineProvider: LifeCycleCoroutineScopeProvider,
    private val batteryMonitorWorkerUseCase: BatteryMonitorWorkerUseCase,
    private val batteryAlertSettingUseCase: BatteryAlertSettingUseCase,
    batteryChangeStatusUseCase: BatteryChangeStatusUseCase,
    workerLogUseCase: WorkerLogUseCase,
) {
    private val viewModelScope = lifecycleCoroutineProvider.coroutineScope()

    val requestPermissionState = MutableSharedFlow<Unit>()
    val batteryStatus = batteryChangeStatusUseCase()
    val isAlertEnabledFlow = batteryAlertSettingUseCase.getAlertEnableStatus()

    val logsFlow = workerLogUseCase.getAll()
        .map { logs ->
            logs.map { log ->
                val formatter = SimpleDateFormat("dd/MM/yy hh:mm a", Locale.getDefault())
                WorkerLog(
                    id = log.id,
                    dateTime = formatter.format(Date(log.timeMillis)),
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