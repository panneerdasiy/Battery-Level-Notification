package iy.panneerdas.batterylevelnotification.di

import android.app.Activity
import android.app.Service
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.lifecycle.LifecycleService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import iy.panneerdas.batterylevelnotification.domain.platform.BatteryChangeStatusProvider
import iy.panneerdas.batterylevelnotification.domain.usecase.status.GetObservableBatteryChangeStatusUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.status.GetObservableBatteryChangeStatusUseCaseImpl
import iy.panneerdas.batterylevelnotification.platform.battery.BatteryChangeStatusProviderImpl
import javax.inject.Qualifier

@InstallIn(ActivityComponent::class, ServiceComponent::class)
@Module
interface BatteryChangeStatusModule {

    companion object {

        @ActivityLifeCycleBatteryStatusProvider
        @Provides
        fun provideActivityLifeCycleBatteryChangeStatusProvider(
            @ApplicationContext context: Context,
            activity: Activity
        ): BatteryChangeStatusProvider {
            return BatteryChangeStatusProviderImpl(
                context = context,
                lifecycle = (activity as ComponentActivity).lifecycle
            )
        }

        @ActivityLifeCycleGetObservableBatteryChangeStatusUseCase
        @Provides
        fun provideActivityLifeCycleGetObservableBatteryChangeStatusUseCase(
            @ActivityLifeCycleBatteryStatusProvider provider: BatteryChangeStatusProvider
        ): GetObservableBatteryChangeStatusUseCase {
            return GetObservableBatteryChangeStatusUseCaseImpl(
                provider = provider
            )
        }

        @ServiceLifeCycleBatteryStatusProvider
        @Provides
        fun provideServiceLifeCycleBatteryChangeStatusProvider(
            @ApplicationContext context: Context,
            service: Service
        ): BatteryChangeStatusProvider {
            return BatteryChangeStatusProviderImpl(
                context = context,
                lifecycle = (service as LifecycleService).lifecycle
            )
        }

        @ServiceLifeCycleGetObservableBatteryChangeStatusUseCase
        @Provides
        fun provideServiceLifeCycleGetObservableBatteryChangeStatusUseCase(
            @ServiceLifeCycleBatteryStatusProvider provider: BatteryChangeStatusProvider
        ): GetObservableBatteryChangeStatusUseCase {
            return GetObservableBatteryChangeStatusUseCaseImpl(
                provider = provider
            )
        }
    }
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityLifeCycleBatteryStatusProvider

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ServiceLifeCycleBatteryStatusProvider

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityLifeCycleGetObservableBatteryChangeStatusUseCase

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ServiceLifeCycleGetObservableBatteryChangeStatusUseCase