package iy.panneerdas.batterylevelnotification.presentation.batterystatus.viewmodel

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryAlertSettingUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryChangeStatusUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryMonitorWorkerUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.worker.WorkerLogUseCase
import iy.panneerdas.batterylevelnotification.presentation.batterystatus.model.WorkerLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
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
    private val viewModelScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) : DefaultLifecycleObserver {

    val requestPermissionState = MutableSharedFlow<Unit>()

    val batteryStatus = batteryChangeStatusUseCase()

    val isAlertEnabledFlow = batteryAlertSettingUseCase.getAlertEnableStatus()

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

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        viewModelScope.cancel()
    }
}