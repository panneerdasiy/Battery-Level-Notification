package iy.panneerdas.batterylevelnotification.platform.notification

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import iy.panneerdas.batterylevelnotification.R
import iy.panneerdas.batterylevelnotification.di.SmartChargeNotificationChannel
import iy.panneerdas.batterylevelnotification.di.SmartChargeNotificationHelper
import iy.panneerdas.batterylevelnotification.domain.model.BatteryStatus
import iy.panneerdas.batterylevelnotification.domain.platform.BatteryAlertHandler
import javax.inject.Inject

class BatteryAlertHandlerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @SmartChargeNotificationChannel private val channel: NotificationChannelCompat,
    @SmartChargeNotificationHelper val notificationHelper: NotificationHelper,
) : BatteryAlertHandler {
    private val startNotificationId = NotificationID.START_CHARGING_NOTIFICATION_ID
    private val stopNotificationId = NotificationID.STOP_CHARGING_NOTIFICATION_ID

    override fun startCharging(status: BatteryStatus) {
        val notification = createNotification(context.getString(R.string.low_battery_notification))
        notificationHelper.showNotification(
            notificationId = startNotificationId,
            notification = notification
        )
    }

    override fun stopCharging(status: BatteryStatus) {
        val notification = createNotification(
            context.getString(R.string.battery_charging_threshold_reached_notification)
        )
        notificationHelper.showNotification(
            notificationId = stopNotificationId,
            notification = incessant(notification)
        )
    }

    override fun dismissStartCharging() {
        notificationHelper.dismiss(startNotificationId)
    }

    override fun dismissStopCharging() {
        notificationHelper.dismiss(stopNotificationId)
    }

    private fun createNotification(content: String): Notification {
        val title = context.getString(R.string.battery_status)
        return NotificationCompat.Builder(context, channel.id)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(content)
            .setAutoCancel(true)
            .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH)
            .build()
    }

    private fun incessant(notification: Notification): Notification {
        return notification.also { it.flags = Notification.FLAG_INSISTENT }
    }
}