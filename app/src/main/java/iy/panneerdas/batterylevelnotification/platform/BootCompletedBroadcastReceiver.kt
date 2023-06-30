package iy.panneerdas.batterylevelnotification.platform

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import iy.panneerdas.batterylevelnotification.domain.usecase.alertservice.HandleBatteryAlertServiceRestartUseCaseImpl
import javax.inject.Inject

@AndroidEntryPoint
class BootCompletedBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var handleSmartChargingRestart: HandleBatteryAlertServiceRestartUseCaseImpl

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            handleSmartChargingRestart()
            return
        }
        throw IllegalArgumentException("Intent action - ${intent.action} is not handled by ${this::class.simpleName}")
    }
}