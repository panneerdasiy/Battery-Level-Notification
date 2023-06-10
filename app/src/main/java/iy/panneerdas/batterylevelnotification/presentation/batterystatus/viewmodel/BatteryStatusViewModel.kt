package iy.panneerdas.batterylevelnotification.presentation.batterystatus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import iy.panneerdas.batterylevelnotification.domain.model.BatteryStatus
import iy.panneerdas.batterylevelnotification.domain.usecase.BatteryAlertSettingUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.BatteryMonitorWorkerUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.BatteryStatusUseCase
import iy.panneerdas.batterylevelnotification.presentation.util.NotificationPermissionManager
import kotlinx.coroutines.launch

class BatteryStatusViewModel(
    batteryStatusUseCase: BatteryStatusUseCase,
    private val batteryMonitorWorkerUseCase: BatteryMonitorWorkerUseCase,
    private val batteryAlertSettingUseCase: BatteryAlertSettingUseCase,
) : ViewModel() {
    lateinit var permissionManager: NotificationPermissionManager

    val batteryStatus: BatteryStatus? = batteryStatusUseCase()

    val alertToggleFlow = batteryAlertSettingUseCase.getAlertEnableStatus()

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