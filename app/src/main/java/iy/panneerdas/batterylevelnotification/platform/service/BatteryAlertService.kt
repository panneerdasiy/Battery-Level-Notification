package iy.panneerdas.batterylevelnotification.platform.service

import android.app.Notification
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.coroutineScope
import dagger.hilt.android.AndroidEntryPoint
import iy.panneerdas.batterylevelnotification.R
import iy.panneerdas.batterylevelnotification.di.BatteryChangeStatusProviderFactory
import iy.panneerdas.batterylevelnotification.di.Dispatcher
import iy.panneerdas.batterylevelnotification.di.DispatcherType
import iy.panneerdas.batterylevelnotification.di.GetObservableBatteryChangeStatusUseCaseFactory
import iy.panneerdas.batterylevelnotification.di.SmartChargeServiceNotificationChannel
import iy.panneerdas.batterylevelnotification.di.SmartChargeServiceNotificationChannelHelper
import iy.panneerdas.batterylevelnotification.domain.usecase.SmartStartAndStopChargeAlertUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.status.GetObservableBatteryChangeStatusUseCase
import iy.panneerdas.batterylevelnotification.platform.notification.NotificationChannelHelper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BatteryAlertService : LifecycleService() {

//    @Inject
//    @Dispatcher(DispatcherType.IO)
//    lateinit var dispatcher: CoroutineDispatcher
    private val dispatcher = Dispatchers.IO

    @SmartChargeServiceNotificationChannelHelper
    @Inject
    lateinit var notificationChannelHelper: NotificationChannelHelper

    @SmartChargeServiceNotificationChannel
    @Inject
    lateinit var notificationChannel: NotificationChannelCompat

    @Inject
    lateinit var getObservableBatteryChangeStatusUseCaseFactory: GetObservableBatteryChangeStatusUseCaseFactory

    @Inject
    lateinit var batteryChangeStatusProviderFactory: BatteryChangeStatusProviderFactory

    @Inject
    lateinit var smartStartAndStopChargeAlertUseCase: SmartStartAndStopChargeAlertUseCase

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
        lifecycle.coroutineScope.launch(dispatcher) {
            getObservableBatteryChangeStatusUseCase().distinctUntilChanged().collect {
                smartStartAndStopChargeAlertUseCase(it)
            }
        }
        return START_STICKY
    }

    private fun startForeground() {
        notificationChannelHelper.createChannelForSDK26Plus()

        val notificationId = 2
        val notification = createNotification(notificationChannel.id)

        startForeground(notificationId, notification)
    }

    private fun createNotification(channelId: String): Notification {
        return NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle(getText(R.string.app_name))
            .setContentText(getText(R.string.smart_charging_monitor_service))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setTicker(getText(R.string.smart_charging_monitor_service_running))
            .setForegroundServiceBehavior(FOREGROUND_SERVICE_IMMEDIATE).build()
    }
}