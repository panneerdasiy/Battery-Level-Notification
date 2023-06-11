package iy.panneerdas.batterylevelnotification.presentation.batterystatus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryAlertSettingUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryChangeStatusUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryMonitorWorkerUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.worker.WorkerLogUseCase

class BatteryStatusViewModelFactory(
    private val batteryMonitorWorkerUseCase: BatteryMonitorWorkerUseCase,
    private val batteryAlertSettingUseCase: BatteryAlertSettingUseCase,
    private val batteryChangeStatusUseCase: BatteryChangeStatusUseCase,
    private val workerLogUseCase: WorkerLogUseCase
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (!modelClass.isAssignableFrom(BatteryStatusViewModel::class.java))
            throw IllegalArgumentException("Unknown view model class")

        return BatteryStatusViewModel(
            batteryMonitorWorkerUseCase = batteryMonitorWorkerUseCase,
            batteryAlertSettingUseCase = batteryAlertSettingUseCase,
            batteryChangeStatusUseCase = batteryChangeStatusUseCase,
            workerLogUseCase = workerLogUseCase
        ) as T
    }
}