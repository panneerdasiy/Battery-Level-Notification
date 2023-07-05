package iy.panneerdas.batterylevelnotification.platform.notification.channel

import androidx.core.app.NotificationChannelCompat

interface NotificationChannelProvider {

    fun getChannel(): NotificationChannelCompat
}