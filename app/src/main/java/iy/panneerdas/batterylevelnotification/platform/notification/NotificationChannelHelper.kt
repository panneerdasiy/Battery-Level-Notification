package iy.panneerdas.batterylevelnotification.platform.notification

import android.os.Build
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationManagerCompat

class NotificationChannelHelper(
    private val manager: NotificationManagerCompat,
    private val channel: NotificationChannelCompat
) {

    fun createChannelForSDK26Plus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(channel)
        }
    }
}