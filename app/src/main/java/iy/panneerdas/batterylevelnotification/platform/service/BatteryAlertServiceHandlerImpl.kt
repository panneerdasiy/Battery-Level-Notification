package iy.panneerdas.batterylevelnotification.platform.service

import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import iy.panneerdas.batterylevelnotification.domain.platform.BatteryAlertServiceHandler
import javax.inject.Inject

class BatteryAlertServiceHandlerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : BatteryAlertServiceHandler {
    private val intent = Intent(context, BatteryMonitorService::class.java)

    override fun scheduleWork() {
        context.startService(intent)
    }

    override fun cancelWork() {
        context.stopService(intent)
    }

}