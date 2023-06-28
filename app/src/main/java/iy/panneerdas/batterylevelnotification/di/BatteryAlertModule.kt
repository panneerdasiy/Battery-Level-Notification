package iy.panneerdas.batterylevelnotification.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import iy.panneerdas.batterylevelnotification.domain.platform.BatteryAlertHandler
import iy.panneerdas.batterylevelnotification.domain.usecase.BatteryAlertUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.BatteryAlertUseCaseImpl
import iy.panneerdas.batterylevelnotification.platform.notification.BatteryAlertHandlerImpl

@InstallIn(SingletonComponent::class)
@Module
interface BatteryAlertModule {

    @Binds
    fun bindBatteryAlertUseCase(useCase: BatteryAlertUseCaseImpl): BatteryAlertUseCase

    @Binds
    fun bindBatteryAlertHandler(useCase: BatteryAlertHandlerImpl): BatteryAlertHandler

}