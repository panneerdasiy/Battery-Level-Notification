package iy.panneerdas.batterylevelnotification.platform

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import iy.panneerdas.batterylevelnotification.domain.usecase.alertservice.RestartHandleBatteryAlertServiceUseCaseImpl
import javax.inject.Inject

@AndroidEntryPoint
class BootCompletedBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var restartSmartCharging: RestartHandleBatteryAlertServiceUseCaseImpl

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            restartSmartCharging()
            return
        }
        throw IllegalArgumentException("Intent action - ${intent.action} is not handled by ${this::class.simpleName}")
    }
}