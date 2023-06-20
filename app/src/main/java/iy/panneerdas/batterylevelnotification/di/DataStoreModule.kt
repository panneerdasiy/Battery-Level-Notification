package iy.panneerdas.batterylevelnotification.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import iy.panneerdas.batterylevelnotification.data.repository.BatteryAlertSettingRepositoryImpl
import iy.panneerdas.batterylevelnotification.domain.repository.BatteryAlertSettingRepository
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryAlertSettingUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryAlertSettingUseCaseImpl

@InstallIn(SingletonComponent::class)
@Module
interface DataStoreModule {

    @Binds
    fun bindBatteryAlertSettingRepository(
        repo: BatteryAlertSettingRepositoryImpl
    ): BatteryAlertSettingRepository

    @Binds
    fun bindBatteryAlertSettingUseCase(
        useCase: BatteryAlertSettingUseCaseImpl
    ): BatteryAlertSettingUseCase
}