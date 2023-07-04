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
    }

    @Binds
    fun bindSmartChargingAlertUseCase(useCase: SmartChargingAlertUseCaseImpl): SmartChargingAlertUseCase

    @Binds
    fun bindBatteryAlertHandler(useCase: BatteryAlertHandlerImpl): BatteryAlertHandler

}