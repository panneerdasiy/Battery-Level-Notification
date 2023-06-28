package iy.panneerdas.batterylevelnotification.platform.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryAlertUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.status.GetBatteryStatusUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.worker.InsertWorkerLogUseCase
import javax.inject.Inject

class BatteryMonitorWorkerFactory @Inject constructor(
    private val getBatteryStatusUseCase: GetBatteryStatusUseCase,
    private val batteryAlertUseCase: BatteryAlertUseCase,
    private val insertWorkerLogUseCase: InsertWorkerLogUseCase
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        return when (workerClassName) {
            BatteryMonitorWorker::class.java.name -> BatteryMonitorWorker(
                context = appContext,
                workerParams = workerParameters,
                getBatteryStatusUseCase = getBatteryStatusUseCase,
                batteryAlertUseCase = batteryAlertUseCase,
                insertWorkerLogUseCase = insertWorkerLogUseCase,
            )

            else -> throw IllegalArgumentException("Unsupported worker class: $workerClassName")
        }
    }
}