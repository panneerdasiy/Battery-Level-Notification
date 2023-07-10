package iy.panneerdas.batterylevelnotification.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import iy.panneerdas.batterylevelnotification.data.repository.alertsetting.BatteryAlertSettingDataStore
import iy.panneerdas.batterylevelnotification.data.repository.alertsetting.BatteryAlertSettingDataStoreImpl
import iy.panneerdas.batterylevelnotification.data.repository.alertsetting.GetObservableBatteryAlertSettingRepositoryImpl
import iy.panneerdas.batterylevelnotification.data.repository.alertsetting.SetBatteryAlertSettingRepositoryImpl
import iy.panneerdas.batterylevelnotification.data.repository.alertsetting.StartChargeAlertEnableStatusDataStore
import iy.panneerdas.batterylevelnotification.data.repository.alertsetting.StartChargeAlertEnableStatusDataStoreImpl
import iy.panneerdas.batterylevelnotification.data.repository.alertsetting.StopChargeAlertEnableStatusDataStore
import iy.panneerdas.batterylevelnotification.data.repository.alertsetting.StopChargeAlertEnableStatusDataStoreImpl
import iy.panneerdas.batterylevelnotification.domain.repository.GetObservableBatteryAlertSettingRepository
import iy.panneerdas.batterylevelnotification.domain.repository.SetBatteryAlertSettingRepository
import iy.panneerdas.batterylevelnotification.domain.usecase.alertsetting.GetObservableBatteryAlertSettingUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.alertsetting.GetObservableBatteryAlertSettingUseCaseImpl
import iy.panneerdas.batterylevelnotification.domain.usecase.alertsetting.GetStartChargeAlertEnableStatusUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.alertsetting.GetStartChargeAlertEnableStatusUseCaseImpl
import iy.panneerdas.batterylevelnotification.domain.usecase.alertsetting.GetStopChargeAlertEnableStatusUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.alertsetting.GetStopChargeAlertEnableStatusUseCaseImpl
import iy.panneerdas.batterylevelnotification.domain.usecase.alertsetting.SetBatteryAlertSettingUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.alertsetting.SetBatteryAlertSettingUseCaseImpl
import iy.panneerdas.batterylevelnotification.domain.usecase.alertsetting.SetStartChargeAlertEnableStatusUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.alertsetting.SetStartChargeAlertEnableStatusUseCaseImpl
import iy.panneerdas.batterylevelnotification.domain.usecase.alertsetting.SetStopChargeAlertEnableStatusUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.alertsetting.SetStopChargeAlertEnableStatusUseCaseImpl
import javax.inject.Qualifier

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "battery_alert_setting")

@InstallIn(SingletonComponent::class)
@Module
interface DataStoreModule {
    companion object {
        @Provides
        @SmartChargeSettings
        fun provideSmartChargeSettings(
            @ApplicationContext context: Context
        ): DataStore<Preferences> {
            return context.dataStore
        }
    }

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

    @Binds
    fun bindGetStartChargeAlertEnableStatusUseCase(
        useCase: GetStartChargeAlertEnableStatusUseCaseImpl
    ): GetStartChargeAlertEnableStatusUseCase

    @Binds
    fun bindSetStartChargeAlertEnableStatusUseCase(
        useCase: SetStartChargeAlertEnableStatusUseCaseImpl
    ): SetStartChargeAlertEnableStatusUseCase

    @Binds
    fun bindGetStopChargeAlertEnableStatusUseCase(
        useCase: GetStopChargeAlertEnableStatusUseCaseImpl
    ): GetStopChargeAlertEnableStatusUseCase

    @Binds
    fun bindSetStopChargeAlertEnableStatusUseCase(
        useCase: SetStopChargeAlertEnableStatusUseCaseImpl
    ): SetStopChargeAlertEnableStatusUseCase

    @Binds
    fun bindStartChargeAlertEnableStatusDataStore(
        useCase: StartChargeAlertEnableStatusDataStoreImpl
    ): StartChargeAlertEnableStatusDataStore

    @Binds
    fun bindStopChargeAlertEnableStatusDataStore(
        useCase: StopChargeAlertEnableStatusDataStoreImpl
    ): StopChargeAlertEnableStatusDataStore
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SmartChargeSettings