package iy.panneerdas.batterylevelnotification.platform.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import iy.panneerdas.batterylevelnotification.domain.usecase.BatteryAlertUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.BatteryStatusUseCase

class BatteryMonitorWorkerFactory(
    private val batteryStatusUseCase: BatteryStatusUseCase,
    private val batteryAlertUseCase: BatteryAlertUseCase
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
            )

            else -> throw IllegalArgumentException("Unsupported worker class: $workerClassName")
        }
    }
}