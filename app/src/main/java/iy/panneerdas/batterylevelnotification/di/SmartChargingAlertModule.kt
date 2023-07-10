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
import iy.panneerdas.batterylevelnotification.domain.platform.BatteryAlertHandler
import iy.panneerdas.batterylevelnotification.domain.usecase.SmartChargeStartAlertUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.SmartChargeStartAlertUseCaseImpl
import iy.panneerdas.batterylevelnotification.domain.usecase.SmartChargeStopAlertUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.SmartChargeStopAlertUseCaseImpl
import iy.panneerdas.batterylevelnotification.domain.usecase.SmartStartAndStopChargeAlertUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.SmartStartAndStopChargeAlertUseCaseImpl
import iy.panneerdas.batterylevelnotification.platform.notification.BatteryAlertHandlerImpl
import iy.panneerdas.batterylevelnotification.platform.notification.NotificationChannelHelper
import iy.panneerdas.batterylevelnotification.platform.notification.NotificationHelper
import iy.panneerdas.batterylevelnotification.platform.notification.channel.SmartChargeServiceNotificationChannelProvider
import iy.panneerdas.batterylevelnotification.platform.notification.channel.SmartChargingNotificationChannelProvider
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
            return SmartChargingNotificationChannelProvider(context).getChannel()
        }

        @SmartChargeServiceNotificationChannel
        @Provides
        fun provideSmartChargeServiceNotificationChannel(
            @ApplicationContext context: Context
        ): NotificationChannelCompat {
            return SmartChargeServiceNotificationChannelProvider(context).getChannel()
        }

        @SmartChargeServiceNotificationChannelHelper
        @Provides
        fun provideSmartChargeServiceNotificationChannelHelper(
            manager: NotificationManagerCompat,
            @SmartChargeServiceNotificationChannel channel: NotificationChannelCompat
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

        @SmartChargeServiceNotificationHelper
        @Provides
        fun provideSmartChargeServiceNotificationHelper(
            manager: NotificationManagerCompat,
            @SmartChargeServiceNotificationChannelHelper notificationChannelHelper: NotificationChannelHelper
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
    fun bindSmartStartAndStopChargeAlertUseCase(useCase: SmartStartAndStopChargeAlertUseCaseImpl)
            : SmartStartAndStopChargeAlertUseCase

    @Binds
    fun bindSmartChargeStartAlertUseCase(useCase: SmartChargeStartAlertUseCaseImpl)
            : SmartChargeStartAlertUseCase

    @Binds
    fun bindSmartChargeStopAlertUseCase(useCase: SmartChargeStopAlertUseCaseImpl)
            : SmartChargeStopAlertUseCase

    @Binds
    fun bindBatteryAlertHandler(useCase: BatteryAlertHandlerImpl): BatteryAlertHandler

}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SmartChargeNotificationChannel

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SmartChargeServiceNotificationChannel

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SmartChargeNotificationChannelHelper

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SmartChargeServiceNotificationChannelHelper

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SmartChargeNotificationHelper

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SmartChargeServiceNotificationHelper