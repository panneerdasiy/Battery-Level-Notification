package iy.panneerdas.batterylevelnotification.platform.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import iy.panneerdas.batterylevelnotification.domain.usecase.BatteryAlertUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.BatteryStatusUseCase

class BatteryMonitorWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val batteryStatusUseCase: BatteryStatusUseCase,
    private val batteryAlertUseCase: BatteryAlertUseCase
) : CoroutineWorker(
    context,
    workerParams
) {
    override suspend fun doWork(): Result {
        val batteryStatus = batteryStatusUseCase() ?: return Result.retry()

        batteryAlertUseCase(batteryStatus)
        return Result.success()
    }
}