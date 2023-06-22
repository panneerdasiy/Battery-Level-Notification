package iy.panneerdas.batterylevelnotification.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import iy.panneerdas.batterylevelnotification.domain.platform.BatteryAlertHandler
import iy.panneerdas.batterylevelnotification.domain.platform.BatteryStatusProvider
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryAlertUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryAlertUseCaseImpl
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryStatusUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryStatusUseCaseImpl
import iy.panneerdas.batterylevelnotification.platform.battery.BatteryStatusProviderImpl
import iy.panneerdas.batterylevelnotification.platform.notification.BatteryAlertHandlerImpl

@InstallIn(SingletonComponent::class)
@Module
interface PlatformWrapperApplicationModule {

    @Binds
    fun bindBatteryAlertUseCase(useCase: BatteryAlertUseCaseImpl): BatteryAlertUseCase

    @Binds
    fun bindBatteryStatusUseCase(useCase: BatteryStatusUseCaseImpl): BatteryStatusUseCase

    @Binds
    fun bindBatteryAlertHandler(useCase: BatteryAlertHandlerImpl): BatteryAlertHandler

    @Binds
    fun bindBatteryStatusProvider(useCase: BatteryStatusProviderImpl): BatteryStatusProvider
}