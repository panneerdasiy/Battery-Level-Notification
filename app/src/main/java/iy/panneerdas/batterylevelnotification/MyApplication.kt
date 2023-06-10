package iy.panneerdas.batterylevelnotification

import android.app.Application
import androidx.work.Configuration
import iy.panneerdas.batterylevelnotification.data.repository.workerlog.AppDatabase
import iy.panneerdas.batterylevelnotification.data.repository.workerlog.WorkerLogRepositoryImpl
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryAlertUseCaseImpl
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryStatusUseCaseImpl
import iy.panneerdas.batterylevelnotification.domain.usecase.worker.WorkerLogUseCaseImpl
import iy.panneerdas.batterylevelnotification.platform.battery.BatteryStatusProviderImpl
import iy.panneerdas.batterylevelnotification.platform.notification.BatteryAlertHandlerImpl
import iy.panneerdas.batterylevelnotification.platform.worker.BatteryMonitorWorkerFactory

class MyApplication : Application(), Configuration.Provider {
    override fun getWorkManagerConfiguration(): Configuration {
        val batteryStatusProvider = BatteryStatusProviderImpl(this)
        val batteryStatusUseCase = BatteryStatusUseCaseImpl(
            batteryStatusProvider = batteryStatusProvider
        )

        val handler = BatteryAlertHandlerImpl(this)
        val batteryAlertUseCase = BatteryAlertUseCaseImpl(handler)

        val appDatabase = AppDatabase.getInstance(this)
        val workerLogDao = appDatabase.workerLogDao()
        val workerLogRepo = WorkerLogRepositoryImpl(workerLogDao)
        val workerLogUseCase = WorkerLogUseCaseImpl(workerLogRepo)

        return Configuration.Builder().setWorkerFactory(
            BatteryMonitorWorkerFactory(
                batteryStatusUseCase = batteryStatusUseCase,
                batteryAlertUseCase = batteryAlertUseCase,
                workerLogUseCase = workerLogUseCase
            )
        ).build()
    }
}