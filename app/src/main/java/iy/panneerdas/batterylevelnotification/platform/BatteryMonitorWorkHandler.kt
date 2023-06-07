package iy.panneerdas.batterylevelnotification.platform

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

interface BatteryMonitorWorkHandler {
    operator fun invoke()
}

class BatteryMonitorWorkHandlerImpl(private val workManager: WorkManager) :
    BatteryMonitorWorkHandler {

    private val workName = "BATTERY_MONITOR"
    private val periodicWork =
        PeriodicWorkRequestBuilder<BatteryMonitorWorker>(
            repeatInterval = 1,
            repeatIntervalTimeUnit = TimeUnit.HOURS,
            flexTimeInterval = 10,
            flexTimeIntervalUnit = TimeUnit.MINUTES,
        ).build()

    override fun invoke() {
        workManager.enqueueUniquePeriodicWork(
            workName,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWork
        )
    }
}