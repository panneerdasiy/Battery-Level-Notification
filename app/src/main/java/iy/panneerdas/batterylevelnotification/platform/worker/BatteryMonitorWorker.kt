package iy.panneerdas.batterylevelnotification.platform.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import iy.panneerdas.batterylevelnotification.data.model.DataWorkerLog
import iy.panneerdas.batterylevelnotification.domain.model.BatteryStatus
import iy.panneerdas.batterylevelnotification.domain.usecase.SmartStartAndStopChargeAlertUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.status.GetBatteryStatusUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.worker.InsertWorkerLogUseCase

class BatteryMonitorWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val getBatteryStatusUseCase: GetBatteryStatusUseCase,
    private val smartStartAndStopChargeAlertUseCase: SmartStartAndStopChargeAlertUseCase,
    private val insertWorkerLogUseCase: InsertWorkerLogUseCase
) : CoroutineWorker(
    context,
    workerParams
) {
    override suspend fun doWork(): Result {
        val batteryStatus = getBatteryStatusUseCase()
        recordLog(batteryStatus)

        if (batteryStatus == null) return Result.retry()

        smartStartAndStopChargeAlertUseCase(batteryStatus)
        return Result.success()
    }

    private fun recordLog(batteryStatus: BatteryStatus?) {
        val log = DataWorkerLog(
            timeMillis = System.currentTimeMillis(),
            batteryPercent = batteryStatus?.percent ?: -1f
        )
        insertWorkerLogUseCase.insert(log = log)
    }
}