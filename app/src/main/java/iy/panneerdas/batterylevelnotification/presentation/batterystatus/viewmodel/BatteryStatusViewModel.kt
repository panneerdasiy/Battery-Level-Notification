package iy.panneerdas.batterylevelnotification.presentation.batterystatus.viewmodel

import androidx.lifecycle.ViewModel
import iy.panneerdas.batterylevelnotification.domain.model.BatteryStatus
import iy.panneerdas.batterylevelnotification.domain.usecase.BatteryStatusUseCase

class BatteryStatusViewModel(useCase: BatteryStatusUseCase) : ViewModel() {
    val batteryStatus: BatteryStatus? = useCase()
}