package iy.panneerdas.batterylevelnotification.platform.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import iy.panneerdas.batterylevelnotification.data.model.WorkerLog
import iy.panneerdas.batterylevelnotification.domain.model.BatteryStatus
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryAlertUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryStatusUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.worker.WorkerLogUseCase

class BatteryMonitorWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val batteryStatusUseCase: BatteryStatusUseCase,
    private val batteryAlertUseCase: BatteryAlertUseCase,
    private val workerLogUseCase: WorkerLogUseCase
) : CoroutineWorker(
    context,
    workerParams
) {
    override suspend fun doWork(): Result {
        val batteryStatus = batteryStatusUseCase()
        recordLog(batteryStatus)

        if (batteryStatus == null) return Result.retry()

        batteryAlertUseCase(batteryStatus)
        return Result.success()
    }

    private fun recordLog(batteryStatus: BatteryStatus?) {
        val log = WorkerLog(
            timeMillis = System.currentTimeMillis(),
            batteryPercent = batteryStatus?.percent ?: -1f
        )
        workerLogUseCase.insert(log = log)
    }
}