package iy.panneerdas.batterylevelnotification.platform.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryAlertUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryStatusUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.worker.InsertWorkerLogUseCase

class BatteryMonitorWorkerFactory(
    private val batteryStatusUseCase: BatteryStatusUseCase,
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
                batteryStatusUseCase = batteryStatusUseCase,
                batteryAlertUseCase = batteryAlertUseCase,
                insertWorkerLogUseCase = insertWorkerLogUseCase,
            )

            else -> throw IllegalArgumentException("Unsupported worker class: $workerClassName")
        }
    }
}