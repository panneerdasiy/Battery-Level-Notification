package iy.panneerdas.batterylevelnotification.presentation.batterystatus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import iy.panneerdas.batterylevelnotification.domain.model.BatteryStatus
import iy.panneerdas.batterylevelnotification.domain.usecase.BatteryStatusUseCase
import iy.panneerdas.batterylevelnotification.presentation.util.NotificationPermissionManager
import kotlinx.coroutines.launch

class BatteryStatusViewModel(
    batteryStatusUseCase: BatteryStatusUseCase,
) : ViewModel() {
    lateinit var permissionManager: NotificationPermissionManager
    val batteryStatus: BatteryStatus? = batteryStatusUseCase()

    private val _alertToggle = MutableLiveData(false)
    val alertToggle: LiveData<Boolean>
        get() = _alertToggle

    fun onAlertToggleChange(isChecked: Boolean) {
        viewModelScope.launch {
            _alertToggle.value = isChecked

            if (isChecked) {
                onAlertToggleEnable()
                return@launch
            }

            disableAlert()
        }
    }

    private suspend fun onAlertToggleEnable() {
        val hasPermission = permissionManager.requestPermission()
        if (hasPermission) {
            enableAlert()
        }

        _alertToggle.value = hasPermission
    }

    private fun disableAlert() {
        TODO("Not yet implemented")
    }

    private fun enableAlert() {
        TODO("Not yet implemented")
    }
}