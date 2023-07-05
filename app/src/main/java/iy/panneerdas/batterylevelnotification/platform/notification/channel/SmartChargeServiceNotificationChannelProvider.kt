package iy.panneerdas.batterylevelnotification.platform.notification.channel

import android.content.Context
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationManagerCompat
import iy.panneerdas.batterylevelnotification.R

class SmartChargeServiceNotificationChannelProvider(context: Context) : NotificationChannelProvider {
    private val channelId = "smart_charging_monitor_service"
    private val importance = NotificationManagerCompat.IMPORTANCE_LOW
    private val name = context.getString(R.string.smart_charging_monitor_service)
    private val description = context.getString(R.string.smart_charging_monitor_service_description)

    override fun getChannel(): NotificationChannelCompat {
        return NotificationChannelCompat.Builder(channelId, importance)
            .setName(name)
            .setDescription(description)
            .setVibrationEnabled(true)
            .build()
    }
}