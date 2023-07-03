package iy.panneerdas.batterylevelnotification.platform.service

import android.app.Notification
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.coroutineScope
import dagger.hilt.android.AndroidEntryPoint
import iy.panneerdas.batterylevelnotification.R
import iy.panneerdas.batterylevelnotification.di.BatteryChangeStatusProviderFactory
import iy.panneerdas.batterylevelnotification.di.GetObservableBatteryChangeStatusUseCaseFactory
import iy.panneerdas.batterylevelnotification.domain.usecase.SmartChargingAlertUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.status.GetObservableBatteryChangeStatusUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BatteryAlertService : LifecycleService() {

    @Inject
    lateinit var getObservableBatteryChangeStatusUseCaseFactory: GetObservableBatteryChangeStatusUseCaseFactory

    @Inject
    lateinit var batteryChangeStatusProviderFactory: BatteryChangeStatusProviderFactory

    @Inject
    lateinit var smartChargingAlertUseCase: SmartChargingAlertUseCase

    private lateinit var getObservableBatteryChangeStatusUseCase: GetObservableBatteryChangeStatusUseCase

    override fun onCreate() {
        super.onCreate()
        getObservableBatteryChangeStatusUseCase =
            getObservableBatteryChangeStatusUseCaseFactory.create(
                lifecycle = lifecycle, providerFactory = batteryChangeStatusProviderFactory
            )
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        startForeground()
        lifecycle.coroutineScope.launch(Dispatchers.IO) {
            getObservableBatteryChangeStatusUseCase().distinctUntilChanged().collect {
                smartChargingAlertUseCase(it)
            }
        }
        return START_STICKY
    }

    private fun startForeground() {
        val channelId = "battery_alert_channel"
        val notificationId = 2
        createChannelForSDK26Plus(channelId)
        val notification = createNotification(channelId)
        startForeground(notificationId, notification)
    }

    private fun createNotification(channelId: String): Notification {
        return NotificationCompat.Builder(applicationContext, channelId)//TODO remove duplicate call
            .setContentTitle(getText(R.string.app_name))
            .setContentText(getText(R.string.battery_monitor_service))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setTicker(getText(R.string.battery_monitor_service_started))
            .setForegroundServiceBehavior(FOREGROUND_SERVICE_IMMEDIATE)
            .build()
    }

    private fun createChannelForSDK26Plus(channelId: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManagerCompat.IMPORTANCE_LOW
            val name = getString(R.string.battery_charging)
            val description = getString(R.string.battery_charging_description)

            val channel = NotificationChannelCompat.Builder(channelId, importance)
                .setName(name)
                .setDescription(description)
                .setVibrationEnabled(true)
                .build()

            NotificationManagerCompat.from(applicationContext).createNotificationChannel(channel)
        }
    }
}