package iy.panneerdas.batterylevelnotification.platform

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

interface BatteryMonitorWorkHandler {
    fun scheduleWork()
    fun cancelWork()
}

class BatteryMonitorWorkHandlerImpl(private val workManager: WorkManager) :
    BatteryMonitorWorkHandler {

    private val uniqueWorkName = "BATTERY_MONITOR"
    private val periodicWork = PeriodicWorkRequestBuilder<BatteryMonitorWorker>(
        repeatInterval = PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS,
        repeatIntervalTimeUnit = TimeUnit.MILLISECONDS,
        flexTimeInterval = PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS,
        flexTimeIntervalUnit = TimeUnit.MILLISECONDS,
    ).build()

    override fun scheduleWork() {
        workManager.enqueueUniquePeriodicWork(
            uniqueWorkName,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWork
        )
    }

    override fun cancelWork() {
        workManager.cancelUniqueWork(uniqueWorkName)
    }
}