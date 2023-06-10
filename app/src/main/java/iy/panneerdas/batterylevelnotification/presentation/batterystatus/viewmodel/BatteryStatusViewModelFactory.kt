package iy.panneerdas.batterylevelnotification.presentation.batterystatus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import iy.panneerdas.batterylevelnotification.domain.usecase.BatteryAlertSettingUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.BatteryMonitorWorkerUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.BatteryStatusUseCase

class BatteryStatusViewModelFactory(
    private val batteryStatusUseCase: BatteryStatusUseCase,
    private val batteryMonitorWorkerUseCase: BatteryMonitorWorkerUseCase,
    private val batteryAlertSettingUseCase: BatteryAlertSettingUseCase,
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (!modelClass.isAssignableFrom(BatteryStatusViewModel::class.java))
            throw IllegalArgumentException("Unknown view model class")

        return BatteryStatusViewModel(
            batteryStatusUseCase = batteryStatusUseCase,
            batteryMonitorWorkerUseCase = batteryMonitorWorkerUseCase,
            batteryAlertSettingUseCase = batteryAlertSettingUseCase,
        ) as T
    }
}