package iy.panneerdas.batterylevelnotification.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import iy.panneerdas.batterylevelnotification.data.repository.alertsetting.BatteryAlertSettingRepositoryImpl
import iy.panneerdas.batterylevelnotification.domain.repository.BatteryAlertSettingRepository
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.GetObservableBatteryAlertSettingUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.GetObservableBatteryAlertSettingUseCaseImpl
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.SetBatteryAlertSettingUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.SetBatteryAlertSettingUseCaseImpl

@InstallIn(SingletonComponent::class)
@Module
interface DataStoreModule {

    @Binds
    fun bindBatteryAlertSettingRepository(
        repo: BatteryAlertSettingRepositoryImpl
    ): BatteryAlertSettingRepository

    @Binds
    fun bindSetBatteryAlertSettingUseCase(
        useCase: SetBatteryAlertSettingUseCaseImpl
    ): SetBatteryAlertSettingUseCase

    @Binds
    fun bindGetObservableBatteryAlertSettingUseCase(
        useCase: GetObservableBatteryAlertSettingUseCaseImpl
    ): GetObservableBatteryAlertSettingUseCase
}