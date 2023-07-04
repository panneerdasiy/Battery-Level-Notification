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
    private val notificationId = 1

    override fun startCharging(status: BatteryStatus) {
        val notification = createNotification(context.getString(R.string.low_battery_notification))
        notificationHelper.showNotification(
            notificationId = notificationId,
            notification = notification
        )
    }

    override fun stopCharging(status: BatteryStatus) {
        val notification = createNotification(
            context.getString(R.string.battery_charging_threshold_reached_notification)
        )
        notificationHelper.showNotification(
            notificationId = notificationId,
            notification = notification
        )
    }

    private fun createNotification(content: String): Notification {
        val title = context.getString(R.string.battery_status)
        return NotificationCompat.Builder(context, channel.id)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH)
            .build()
    }
}