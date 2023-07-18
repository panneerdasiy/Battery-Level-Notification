package iy.panneerdas.batterylevelnotification.di

import android.app.Service
import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleService
import dagger.Module
import dagger.Provides
import dagger.assisted.AssistedFactory
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import iy.panneerdas.batterylevelnotification.domain.platform.BatteryChangeStatusProvider
import iy.panneerdas.batterylevelnotification.domain.usecase.status.GetObservableBatteryChangeStatusUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.status.GetObservableBatteryChangeStatusUseCaseImpl
import iy.panneerdas.batterylevelnotification.platform.battery.BatteryChangeStatusProviderImpl
import javax.inject.Qualifier

@InstallIn(ViewModelComponent::class, ServiceComponent::class)
@Module
interface BatteryChangeStatusModule {

    companion object {

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

@AssistedFactory
interface BatteryChangeStatusProviderFactory {

    fun create(lifecycle: Lifecycle): BatteryChangeStatusProviderImpl

}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ServiceLifeCycleBatteryStatusProvider

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ServiceLifeCycleGetObservableBatteryChangeStatusUseCase