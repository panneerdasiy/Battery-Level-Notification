package iy.panneerdas.batterylevelnotification.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import iy.panneerdas.batterylevelnotification.domain.platform.BatteryStatusProvider
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryStatusUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryStatusUseCaseImpl
import iy.panneerdas.batterylevelnotification.platform.battery.BatteryStatusProviderImpl

@InstallIn(SingletonComponent::class)
@Module
interface BatteryStatusModule {
    @Binds
    fun bindBatteryStatusUseCase(useCase: BatteryStatusUseCaseImpl): BatteryStatusUseCase

    @Binds
    fun bindBatteryStatusProvider(useCase: BatteryStatusProviderImpl): BatteryStatusProvider
}