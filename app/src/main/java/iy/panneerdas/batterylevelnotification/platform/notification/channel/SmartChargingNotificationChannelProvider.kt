package iy.panneerdas.batterylevelnotification.platform.notification.channel

import android.content.Context
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationManagerCompat
import iy.panneerdas.batterylevelnotification.R

class SmartChargingNotificationChannelProvider(context: Context) : NotificationChannelProvider {
    private val channelId = "battery_alert_channel"
    private val importance = NotificationManagerCompat.IMPORTANCE_HIGH
    private val name = context.getString(R.string.battery_charging)
    private val description = context.getString(R.string.battery_charging_description)

    override fun getChannel(): NotificationChannelCompat {
        return NotificationChannelCompat.Builder(channelId, importance)
            .setName(name)
            .setDescription(description)
            .setVibrationEnabled(true)
            .build()
    }
}