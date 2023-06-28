package iy.panneerdas.batterylevelnotification.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import iy.panneerdas.batterylevelnotification.domain.platform.BatteryStatusProvider
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.status.GetBatteryStatusUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.status.GetBatteryStatusUseCaseImpl
import iy.panneerdas.batterylevelnotification.platform.battery.BatteryStatusProviderImpl

@InstallIn(SingletonComponent::class)
@Module
interface BatteryStatusModule {
    @Binds
    fun bindBatteryStatusUseCase(useCase: GetBatteryStatusUseCaseImpl): GetBatteryStatusUseCase

    @Binds
    fun bindBatteryStatusProvider(useCase: BatteryStatusProviderImpl): BatteryStatusProvider
}