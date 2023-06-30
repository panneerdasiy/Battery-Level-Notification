package iy.panneerdas.batterylevelnotification

import android.app.Application
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import iy.panneerdas.batterylevelnotification.domain.usecase.alertservice.HandleBatteryAlertServiceRestartUseCaseImpl
import iy.panneerdas.batterylevelnotification.platform.worker.BatteryMonitorWorkerFactory
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var batteryMonitorWorkerFactory: BatteryMonitorWorkerFactory

    @Inject
    lateinit var handleSmartChargingRestart: HandleBatteryAlertServiceRestartUseCaseImpl

    override fun onCreate() {
        super.onCreate()
        handleSmartChargingRestart()
    }

    override fun getWorkManagerConfiguration(): Configuration {

        return Configuration.Builder().setWorkerFactory(
            batteryMonitorWorkerFactory
        ).build()
    }
}