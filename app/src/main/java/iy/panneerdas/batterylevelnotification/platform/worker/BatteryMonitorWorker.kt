package iy.panneerdas.batterylevelnotification.platform.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
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
        workerLogUseCase.insert()

        val batteryStatus = batteryStatusUseCase() ?: return Result.retry()
        batteryAlertUseCase(batteryStatus)
        return Result.success()
    }
}