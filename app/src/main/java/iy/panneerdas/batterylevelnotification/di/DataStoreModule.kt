package iy.panneerdas.batterylevelnotification.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import iy.panneerdas.batterylevelnotification.data.repository.alertsetting.BatteryAlertSettingDataStore
import iy.panneerdas.batterylevelnotification.data.repository.alertsetting.BatteryAlertSettingDataStoreImpl
import iy.panneerdas.batterylevelnotification.data.repository.alertsetting.GetObservableBatteryAlertSettingRepositoryImpl
import iy.panneerdas.batterylevelnotification.data.repository.alertsetting.SetBatteryAlertSettingRepositoryImpl
import iy.panneerdas.batterylevelnotification.domain.repository.GetObservableBatteryAlertSettingRepository
import iy.panneerdas.batterylevelnotification.domain.repository.SetBatteryAlertSettingRepository
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.GetObservableBatteryAlertSettingUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.GetObservableBatteryAlertSettingUseCaseImpl
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.SetBatteryAlertSettingUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.SetBatteryAlertSettingUseCaseImpl

@InstallIn(SingletonComponent::class)
@Module
interface DataStoreModule {

    @Binds
    fun bindBatteryAlertSettingDataStore(
        repo: BatteryAlertSettingDataStoreImpl
    ): BatteryAlertSettingDataStore

    @Binds
    fun bindSetBatteryAlertSettingRepository(
        repo: SetBatteryAlertSettingRepositoryImpl
    ): SetBatteryAlertSettingRepository

    @Binds
    fun bindGetObservableBatteryAlertSettingRepository(
        repo: GetObservableBatteryAlertSettingRepositoryImpl
    ): GetObservableBatteryAlertSettingRepository

    @Binds
    fun bindSetBatteryAlertSettingUseCase(
        useCase: SetBatteryAlertSettingUseCaseImpl
    ): SetBatteryAlertSettingUseCase

    @Binds
    fun bindGetObservableBatteryAlertSettingUseCase(
        useCase: GetObservableBatteryAlertSettingUseCaseImpl
    ): GetObservableBatteryAlertSettingUseCase
}