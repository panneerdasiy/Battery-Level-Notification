package iy.panneerdas.batterylevelnotification.platform

import android.annotation.SuppressLint
import android.app.Notification
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import iy.panneerdas.batterylevelnotification.R
import iy.panneerdas.batterylevelnotification.domain.model.BatteryStatus

interface BatteryAlertHandler {

    fun startCharging(status: BatteryStatus)

    fun stopCharging(status: BatteryStatus)
}

class BatteryAlertHandlerImpl(private val context: Context) : BatteryAlertHandler {
    private val channelId = "battery_alert_channel"
    private val notificationId = 1
    private val manager = NotificationManagerCompat.from(context)

    override fun startCharging(status: BatteryStatus) {
        showNotification(context.getString(R.string.low_battery_notification))
    }

    override fun stopCharging(status: BatteryStatus) {
        showNotification(context.getString(R.string.battery_charging_threshold_reached_notification))
    }

    @SuppressLint("MissingPermission")
    private fun showNotification(content: String) {
        if (!manager.areNotificationsEnabled()) return

        createChannelForSDK26Plus()

        val notification = createNotification(content)
        manager.notify(notificationId, notification)
    }

    private fun createNotification(content: String): Notification {
        val title = context.getString(R.string.battery_status)
        return NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH)
            .build()
    }

    private fun createChannelForSDK26Plus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManagerCompat.IMPORTANCE_HIGH
            val name = context.getString(R.string.battery_charging)
            val description = context.getString(R.string.battery_charging_description)

            val channel = NotificationChannelCompat.Builder(channelId, importance)
                .setName(name)
                .setDescription(description)
                .setVibrationEnabled(true)
                .build()

            manager.createNotificationChannel(channel)
        }
    }

}