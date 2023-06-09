package iy.panneerdas.batterylevelnotification

import android.app.Application
import androidx.work.Configuration
import iy.panneerdas.batterylevelnotification.domain.usecase.BatteryAlertUseCaseImpl
import iy.panneerdas.batterylevelnotification.domain.usecase.BatteryStatusUseCaseImpl
import iy.panneerdas.batterylevelnotification.platform.notification.BatteryAlertHandlerImpl
import iy.panneerdas.batterylevelnotification.platform.worker.BatteryMonitorWorkerFactory
import iy.panneerdas.batterylevelnotification.platform.battery.BatteryStatusProviderImpl

class MyApplication : Application(), Configuration.Provider {
    override fun getWorkManagerConfiguration(): Configuration {
        val batteryStatusProvider = BatteryStatusProviderImpl(this)
        val batteryStatusUseCase = BatteryStatusUseCaseImpl(
            batteryStatusProvider = batteryStatusProvider
        )
        val handler = BatteryAlertHandlerImpl(context = this)
        val batteryAlertUseCase = BatteryAlertUseCaseImpl(handler = handler)

        return Configuration.Builder()
            .setWorkerFactory(
                BatteryMonitorWorkerFactory(
                    batteryStatusUseCase = batteryStatusUseCase,
                    batteryAlertUseCase = batteryAlertUseCase
                )
            )
            .build()
    }
}