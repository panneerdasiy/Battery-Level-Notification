package iy.panneerdas.batterylevelnotification.platform.notification

import android.annotation.SuppressLint
import android.app.Notification
import android.os.Build
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationManagerCompat

abstract class NotificationHelperTemplate(
    private val manager: NotificationManagerCompat,
    private val channel: NotificationChannelCompat,
) {

    @SuppressLint("MissingPermission")
    fun showNotification(notificationId: Int, notification: Notification) {
        if (!manager.areNotificationsEnabled()) return

        createChannelForSDK26Plus()

        manager.notify(notificationId, notification)
    }

    private fun createChannelForSDK26Plus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(channel)
        }
    }
}