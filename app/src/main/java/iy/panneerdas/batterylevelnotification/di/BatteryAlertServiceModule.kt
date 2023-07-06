package iy.panneerdas.batterylevelnotification.di

import android.content.Context
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import iy.panneerdas.batterylevelnotification.domain.platform.StartBatteryAlertServiceHandler
import iy.panneerdas.batterylevelnotification.domain.platform.StopBatteryAlertServiceHandler
import iy.panneerdas.batterylevelnotification.domain.usecase.alertservice.StartSmartChargeServiceIfAllowedUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.alertservice.StartSmartChargeServiceIfAllowedUseCaseImpl
import iy.panneerdas.batterylevelnotification.domain.usecase.alertservice.StartBatteryAlertServiceUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.alertservice.StartBatteryAlertServiceUseCaseImpl
import iy.panneerdas.batterylevelnotification.domain.usecase.alertservice.StartBatteryAlertWorkerServiceUseCaseImpl
import iy.panneerdas.batterylevelnotification.domain.usecase.alertservice.StopBatteryAlertServiceUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.alertservice.StopBatteryAlertServiceUseCaseImpl
import iy.panneerdas.batterylevelnotification.domain.usecase.alertservice.StopBatteryAlertWorkerServiceUseCaseImpl
import iy.panneerdas.batterylevelnotification.platform.service.BatteryAlertServiceHandlerImpl
import javax.inject.Qualifier

@InstallIn(SingletonComponent::class)
@Module
class BatteryAlertServiceModule {

    @Provides
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @StartAlertWorker
    @Provides
    fun provideStartBatteryAlertWorkerServiceHandler(
        handler: BatteryAlertServiceHandlerImpl
    ): StartBatteryAlertServiceHandler {
        return handler
    }

    @StartAlertWorker
    @Provides
    fun provideStartBatteryAlertWorkerServiceUseCase(
        useCase: StartBatteryAlertWorkerServiceUseCaseImpl
    ): StartBatteryAlertServiceUseCase {
        return useCase
    }

    @StartAlertService
    @Provides
    fun provideStartBatteryAlertServiceHandler(
        handler: BatteryAlertServiceHandlerImpl
    ): StartBatteryAlertServiceHandler {
        return handler
    }

    @StartAlertService
    @Provides
    fun provideStartBatteryAlertServiceUseCase(
        useCase: StartBatteryAlertServiceUseCaseImpl
    ): StartBatteryAlertServiceUseCase {
        return useCase
    }

    @StopAlertWorker
    @Provides
    fun provideStopBatteryAlertWorkerServiceHandler(
        handler: BatteryAlertServiceHandlerImpl
    ): StopBatteryAlertServiceHandler {
        return handler
    }

    @StopAlertWorker
    @Provides
    fun provideStopBatteryAlertWorkerServiceUseCase(
        useCase: StopBatteryAlertWorkerServiceUseCaseImpl
    ): StopBatteryAlertServiceUseCase {
        return useCase
    }

    @StopAlertService
    @Provides
    fun provideStopBatteryAlertServiceHandler(
        handler: BatteryAlertServiceHandlerImpl
    ): StopBatteryAlertServiceHandler {
        return handler
    }

    @StopAlertService
    @Provides
    fun provideStopBatteryAlertServiceUseCase(
        useCase: StopBatteryAlertServiceUseCaseImpl
    ): StopBatteryAlertServiceUseCase {
        return useCase
    }

    @Provides
    fun provideStartSmartChargeServiceIfAllowedUseCase(
        useCase: StartSmartChargeServiceIfAllowedUseCaseImpl
    ): StartSmartChargeServiceIfAllowedUseCase {
        return useCase
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class StartAlertService

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class StartAlertWorker

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class StopAlertService

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class StopAlertWorker