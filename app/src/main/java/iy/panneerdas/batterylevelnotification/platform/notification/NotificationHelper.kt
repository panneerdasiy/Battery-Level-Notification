package iy.panneerdas.batterylevelnotification.platform.notification

import android.annotation.SuppressLint
import android.app.Notification
import androidx.core.app.NotificationManagerCompat

class NotificationHelper(
    private val manager: NotificationManagerCompat,
    private val notificationChannelHelper: NotificationChannelHelper
) {

    @SuppressLint("MissingPermission")
    fun showNotification(notificationId: Int, notification: Notification) {
        if (!manager.areNotificationsEnabled()) return

        notificationChannelHelper.createChannelForSDK26Plus()

        manager.notify(notificationId, notification)
    }

    fun dismiss(notificationId: Int) {
        manager.cancel(notificationId)
    }
}