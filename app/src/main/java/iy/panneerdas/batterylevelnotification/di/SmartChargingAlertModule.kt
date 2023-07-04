package iy.panneerdas.batterylevelnotification.di

import android.content.Context
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationManagerCompat
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import iy.panneerdas.batterylevelnotification.R
import iy.panneerdas.batterylevelnotification.domain.platform.BatteryAlertHandler
import iy.panneerdas.batterylevelnotification.domain.usecase.SmartChargingAlertUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.SmartChargingAlertUseCaseImpl
import iy.panneerdas.batterylevelnotification.platform.notification.BatteryAlertHandlerImpl
import iy.panneerdas.batterylevelnotification.platform.notification.NotificationChannelHelper
import iy.panneerdas.batterylevelnotification.platform.notification.NotificationHelper
import javax.inject.Qualifier

@InstallIn(SingletonComponent::class)
@Module
interface SmartChargingAlertModule {

    companion object {
        @Provides
        fun provideNotificationManagerCompat(
            @ApplicationContext context: Context
        ): NotificationManagerCompat {
            return NotificationManagerCompat.from(context)
        }

        @SmartChargeNotificationChannel
        @Provides
        fun provideSmartChargeNotificationChannel(
            @ApplicationContext context: Context
        ): NotificationChannelCompat {
            val channelId = "battery_alert_channel"
            val importance = NotificationManagerCompat.IMPORTANCE_HIGH
            val name = context.getString(R.string.battery_charging)
            val description = context.getString(R.string.battery_charging_description)

            return NotificationChannelCompat.Builder(channelId, importance)
                .setName(name)
                .setDescription(description)
                .setVibrationEnabled(true)
                .build()
        }

        @SmartChargeForegroundNotificationChannel
        @Provides
        fun provideSmartChargeForegroundNotificationChannel(
            @ApplicationContext context: Context
        ): NotificationChannelCompat {
            val channelId = "smart_charging_monitor_service"
            val importance = NotificationManagerCompat.IMPORTANCE_LOW
            val name = context.getString(R.string.smart_charging_monitor_service)
            val description = context.getString(R.string.smart_charging_monitor_service_description)

            return NotificationChannelCompat.Builder(channelId, importance)
                .setName(name)
                .setDescription(description)
                .setVibrationEnabled(true)
                .build()
        }

        @SmartChargeForegroundNotificationChannelHelper
        @Provides
        fun provideSmartChargeForegroundNotificationChannelHelper(
            manager: NotificationManagerCompat,
            @SmartChargeForegroundNotificationChannel channel: NotificationChannelCompat
        ): NotificationChannelHelper {
            return NotificationChannelHelper(manager = manager, channel = channel)
        }

        @SmartChargeNotificationChannelHelper
        @Provides
        fun provideSmartChargeNotificationChannelHelper(
            manager: NotificationManagerCompat,
            @SmartChargeNotificationChannel channel: NotificationChannelCompat
        ): NotificationChannelHelper {
            return NotificationChannelHelper(manager = manager, channel = channel)
        }

        @SmartChargeForegroundNotificationHelper
        @Provides
        fun provideSmartChargeForegroundNotificationHelper(
            manager: NotificationManagerCompat,
            @SmartChargeForegroundNotificationChannelHelper notificationChannelHelper: NotificationChannelHelper
        ): NotificationHelper {
            return NotificationHelper(
                manager = manager,
                notificationChannelHelper = notificationChannelHelper
            )
        }

        @SmartChargeNotificationHelper
        @Provides
        fun provideSmartChargeNotificationHelper(
            manager: NotificationManagerCompat,
            @SmartChargeNotificationChannelHelper notificationChannelHelper: NotificationChannelHelper
        ): NotificationHelper {
            return NotificationHelper(
                manager = manager,
                notificationChannelHelper = notificationChannelHelper
            )
        }
    }

    @Binds
    fun bindSmartChargingAlertUseCase(useCase: SmartChargingAlertUseCaseImpl): SmartChargingAlertUseCase

    @Binds
    fun bindBatteryAlertHandler(useCase: BatteryAlertHandlerImpl): BatteryAlertHandler

}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SmartChargeNotificationChannel

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SmartChargeForegroundNotificationChannel

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SmartChargeNotificationChannelHelper

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SmartChargeForegroundNotificationChannelHelper

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SmartChargeNotificationHelper

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SmartChargeForegroundNotificationHelper