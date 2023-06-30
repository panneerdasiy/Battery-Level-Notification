package iy.panneerdas.batterylevelnotification.platform.worker

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import iy.panneerdas.batterylevelnotification.domain.platform.BatteryAlertServiceHandler
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class BatteryAlertWorkerServiceHandlerImpl @Inject constructor(private val workManager: WorkManager) :
    BatteryAlertServiceHandler {

    private val uniqueWorkName = "BATTERY_MONITOR"
    private val periodicWork = PeriodicWorkRequestBuilder<BatteryMonitorWorker>(
        repeatInterval = PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS,
        repeatIntervalTimeUnit = TimeUnit.MILLISECONDS,
        flexTimeInterval = PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS,
        flexTimeIntervalUnit = TimeUnit.MILLISECONDS,
    ).build()

    override fun start() {
        workManager.enqueueUniquePeriodicWork(
            uniqueWorkName,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWork
        )
    }

    override fun stop() {
        workManager.cancelUniqueWork(uniqueWorkName)
    }
}