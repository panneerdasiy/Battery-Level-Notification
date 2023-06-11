package iy.panneerdas.batterylevelnotification.presentation.batterystatus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryAlertSettingUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryChangeStatusUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryMonitorWorkerUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.worker.WorkerLogUseCase
import iy.panneerdas.batterylevelnotification.presentation.batterystatus.model.WorkerLog
import iy.panneerdas.batterylevelnotification.presentation.util.NotificationPermissionManager
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Locale

class BatteryStatusViewModel(
    private val batteryMonitorWorkerUseCase: BatteryMonitorWorkerUseCase,
    private val batteryAlertSettingUseCase: BatteryAlertSettingUseCase,
    batteryChangeStatusUseCase: BatteryChangeStatusUseCase,
    workerLogUseCase: WorkerLogUseCase,
) : ViewModel() {
    lateinit var permissionManager: NotificationPermissionManager

    val batteryStatus = batteryChangeStatusUseCase()//TODO map to presenter model

    val alertToggleFlow = batteryAlertSettingUseCase.getAlertEnableStatus()

    val logsFlow = workerLogUseCase.getAll()
        .map { logs ->
            logs.map { log ->
                val formatter = SimpleDateFormat("dd/MM/yy hh:mm a", Locale.getDefault())
                WorkerLog(
                    id = log.id,
                    dateTime = formatter.format(Date(log.timeMillis))
                )
            }
        }

    fun onAlertToggleChange(isChecked: Boolean) {
        viewModelScope.launch {
            if (isChecked) {
                onAlertToggleEnable()
                return@launch
            }

            disableAlert()
        }
    }

    private suspend fun onAlertToggleEnable() {
        val hasPermission = permissionManager.requestPermission()
        if (hasPermission) enableAlert()
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