package iy.panneerdas.batterylevelnotification

import android.app.Application
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import iy.panneerdas.batterylevelnotification.domain.usecase.alertservice.RestartHandleBatteryAlertServiceUseCaseImpl
import iy.panneerdas.batterylevelnotification.platform.worker.BatteryMonitorWorkerFactory
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var batteryMonitorWorkerFactory: BatteryMonitorWorkerFactory

    @Inject
    lateinit var restartSmartCharging: RestartHandleBatteryAlertServiceUseCaseImpl

    override fun onCreate() {
        super.onCreate()
        restartSmartCharging()
    }

    override fun getWorkManagerConfiguration(): Configuration {

        return Configuration.Builder().setWorkerFactory(
            batteryMonitorWorkerFactory
        ).build()
    }
}