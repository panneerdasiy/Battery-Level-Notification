package iy.panneerdas.batterylevelnotification.di

import android.content.Context
import androidx.work.WorkManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import iy.panneerdas.batterylevelnotification.domain.platform.BatteryMonitorWorkHandler
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryMonitorWorkerUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryMonitorWorkerUseCaseImpl
import iy.panneerdas.batterylevelnotification.platform.worker.BatteryMonitorWorkHandlerImpl

@InstallIn(SingletonComponent::class)
@Module
abstract class WorkManagerModule {

    companion object {
        @Provides
        fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
            return WorkManager.getInstance(context)
        }
    }

    @Binds
    abstract fun bindBatteryMonitorWorkHandler(
        handler: BatteryMonitorWorkHandlerImpl
    ): BatteryMonitorWorkHandler

    @Binds
    abstract fun bindBatteryMonitorWorkerUseCase(
        useCase: BatteryMonitorWorkerUseCaseImpl
    ): BatteryMonitorWorkerUseCase
}