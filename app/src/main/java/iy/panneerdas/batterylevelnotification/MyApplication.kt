package iy.panneerdas.batterylevelnotification

import android.app.Application
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import iy.panneerdas.batterylevelnotification.di.StartMonitorService
import iy.panneerdas.batterylevelnotification.domain.usecase.alertservice.StartBatteryAlertServiceUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.alertsetting.GetObservableBatteryAlertSettingUseCase
import iy.panneerdas.batterylevelnotification.platform.worker.BatteryMonitorWorkerFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var batteryMonitorWorkerFactory: BatteryMonitorWorkerFactory

    @Inject
    lateinit var getObservableBatteryAlertSettingUseCase: GetObservableBatteryAlertSettingUseCase

    @StartMonitorService
    @Inject
    lateinit var startBatteryAlertServiceUseCase: StartBatteryAlertServiceUseCase

    override fun onCreate() {
        super.onCreate()
        handleSmartChargingServiceRestart()
    }

    override fun getWorkManagerConfiguration(): Configuration {

        return Configuration.Builder().setWorkerFactory(
            batteryMonitorWorkerFactory
        ).build()
    }

    private fun handleSmartChargingServiceRestart() {
        CoroutineScope(Dispatchers.IO).launch {
            val isSmartChargingAlertEnabled = getObservableBatteryAlertSettingUseCase().first()
            if (isSmartChargingAlertEnabled)
                startBatteryAlertServiceUseCase.start()
        }
    }
}