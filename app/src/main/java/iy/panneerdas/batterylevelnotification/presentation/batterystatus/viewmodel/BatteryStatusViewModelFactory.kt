package iy.panneerdas.batterylevelnotification.presentation.batterystatus.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import iy.panneerdas.batterylevelnotification.domain.usecase.BatteryStatusUseCaseImpl
import iy.panneerdas.batterylevelnotification.platform.BatteryStatusProviderImpl

class BatteryStatusViewModelFactory(private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (!modelClass.isAssignableFrom(BatteryStatusViewModel::class.java))
            throw IllegalArgumentException("Unknown view model class")

        val batteryStatusProvider = BatteryStatusProviderImpl(context)
        val batteryStatusUseCase = BatteryStatusUseCaseImpl(batteryStatusProvider)
        return BatteryStatusViewModel(batteryStatusUseCase) as T
    }
}