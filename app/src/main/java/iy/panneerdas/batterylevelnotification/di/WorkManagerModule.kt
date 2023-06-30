package iy.panneerdas.batterylevelnotification.di

import android.content.Context
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import iy.panneerdas.batterylevelnotification.domain.platform.BatteryAlertServiceHandler
import iy.panneerdas.batterylevelnotification.domain.usecase.BatteryMonitorServiceUseCaseImpl
import iy.panneerdas.batterylevelnotification.domain.usecase.BatteryMonitorWorkerUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.BatteryMonitorWorkerUseCaseImpl
import iy.panneerdas.batterylevelnotification.platform.service.BatteryAlertServiceHandlerImpl
import javax.inject.Qualifier

@InstallIn(SingletonComponent::class)
@Module
class WorkManagerModule {

    @Provides
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @MonitorWorker
    @Provides
    fun provideBatteryAlertWorkerServiceHandler(
        handler: BatteryAlertServiceHandlerImpl
    ): BatteryAlertServiceHandler {
        return handler
    }

    @MonitorWorker
    @Provides
    fun provideBatteryMonitorWorkerUseCase(
        useCase: BatteryMonitorWorkerUseCaseImpl
    ): BatteryMonitorWorkerUseCase {
        return useCase
    }

    @MonitorService
    @Provides
    fun provideBatteryAlertServiceHandler(
        handler: BatteryAlertServiceHandlerImpl
    ): BatteryAlertServiceHandler {
        return handler
    }

    @MonitorService
    @Provides
    fun provideBatteryMonitorServiceUseCase(
        useCase: BatteryMonitorServiceUseCaseImpl
    ): BatteryMonitorWorkerUseCase {
        return useCase
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MonitorService

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MonitorWorker