package iy.panneerdas.batterylevelnotification.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import iy.panneerdas.batterylevelnotification.domain.platform.BatteryAlertHandler
import iy.panneerdas.batterylevelnotification.domain.usecase.SmartChargingAlertUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.SmartChargingAlertUseCaseImpl
import iy.panneerdas.batterylevelnotification.platform.notification.BatteryAlertHandlerImpl

@InstallIn(SingletonComponent::class)
@Module
interface BatteryAlertModule {

    @Binds
    fun bindSmartChargingAlertUseCase(useCase: SmartChargingAlertUseCaseImpl): SmartChargingAlertUseCase

    @Binds
    fun bindBatteryAlertHandler(useCase: BatteryAlertHandlerImpl): BatteryAlertHandler

}