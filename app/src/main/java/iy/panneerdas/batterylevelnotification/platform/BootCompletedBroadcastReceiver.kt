package iy.panneerdas.batterylevelnotification.platform

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import iy.panneerdas.batterylevelnotification.di.StartMonitorService
import iy.panneerdas.batterylevelnotification.domain.usecase.alertservice.StartBatteryAlertServiceUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.alertsetting.GetObservableBatteryAlertSettingUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootCompletedBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var getObservableBatteryAlertSettingUseCase: GetObservableBatteryAlertSettingUseCase

    @StartMonitorService
    @Inject
    lateinit var startBatteryAlertServiceUseCase: StartBatteryAlertServiceUseCase

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            onBootCompleted()
            return
        }
        throw IllegalArgumentException("Intent action - ${intent.action} is not handled by ${this::class.simpleName}")
    }

    private fun onBootCompleted() {
        CoroutineScope(Dispatchers.IO).launch {
            val isSmartChargingAlertEnabled = getObservableBatteryAlertSettingUseCase().first()
            if (isSmartChargingAlertEnabled)
                startBatteryAlertServiceUseCase.start()
        }
    }
}