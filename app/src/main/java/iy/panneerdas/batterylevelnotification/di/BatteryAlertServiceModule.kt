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
import iy.panneerdas.batterylevelnotification.domain.usecase.alertservice.RestartHandleBatteryAlertServiceUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.alertservice.RestartHandleBatteryAlertServiceUseCaseImpl
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

    @StartMonitorWorker
    @Provides
    fun provideStartBatteryAlertWorkerServiceHandler(
        handler: BatteryAlertServiceHandlerImpl
    ): StartBatteryAlertServiceHandler {
        return handler
    }

    @StartMonitorWorker
    @Provides
    fun provideStartBatteryAlertWorkerServiceUseCase(
        useCase: StartBatteryAlertWorkerServiceUseCaseImpl
    ): StartBatteryAlertServiceUseCase {
        return useCase
    }

    @StartMonitorService
    @Provides
    fun provideStartBatteryAlertServiceHandler(
        handler: BatteryAlertServiceHandlerImpl
    ): StartBatteryAlertServiceHandler {
        return handler
    }

    @StartMonitorService
    @Provides
    fun provideStartBatteryAlertServiceUseCase(
        useCase: StartBatteryAlertServiceUseCaseImpl
    ): StartBatteryAlertServiceUseCase {
        return useCase
    }

    @StopMonitorWorker
    @Provides
    fun provideStopBatteryAlertWorkerServiceHandler(
        handler: BatteryAlertServiceHandlerImpl
    ): StopBatteryAlertServiceHandler {
        return handler
    }

    @StopMonitorWorker
    @Provides
    fun provideStopBatteryAlertWorkerServiceUseCase(
        useCase: StopBatteryAlertWorkerServiceUseCaseImpl
    ): StopBatteryAlertServiceUseCase {
        return useCase
    }

    @StopMonitorService
    @Provides
    fun provideStopBatteryAlertServiceHandler(
        handler: BatteryAlertServiceHandlerImpl
    ): StopBatteryAlertServiceHandler {
        return handler
    }

    @StopMonitorService
    @Provides
    fun provideStopBatteryAlertServiceUseCase(
        useCase: StopBatteryAlertServiceUseCaseImpl
    ): StopBatteryAlertServiceUseCase {
        return useCase
    }

    @Provides
    fun provideRestartBatteryAlertServiceUseCase(
        useCase: RestartHandleBatteryAlertServiceUseCaseImpl
    ): RestartHandleBatteryAlertServiceUseCase {
        return useCase
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class StartMonitorService

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class StartMonitorWorker

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class StopMonitorService

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class StopMonitorWorker