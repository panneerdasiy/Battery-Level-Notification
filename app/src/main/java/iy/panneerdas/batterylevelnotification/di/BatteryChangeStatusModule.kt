package iy.panneerdas.batterylevelnotification.di

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import dagger.Module
import dagger.Provides
import dagger.assisted.AssistedFactory
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import iy.panneerdas.batterylevelnotification.domain.platform.BatteryChangeStatusProvider
import iy.panneerdas.batterylevelnotification.domain.usecase.status.GetObservableBatteryChangeStatusUseCaseImpl
import iy.panneerdas.batterylevelnotification.platform.battery.BatteryChangeStatusProviderImpl

@InstallIn(ActivityComponent::class)
@Module
interface BatteryChangeStatusModule {
    companion object {
        @Provides
        fun provideComponentActivity(
            @ActivityContext context: Context
        ): ComponentActivity {
            return context as ComponentActivity
        }
    }
}

@AssistedFactory
interface BatteryChangeStatusProviderFactory {
    fun create(lifecycle: Lifecycle): BatteryChangeStatusProviderImpl
}

@AssistedFactory
abstract class GetObservableBatteryChangeStatusUseCaseFactory {
    abstract fun create(
        provider: BatteryChangeStatusProvider
    ): GetObservableBatteryChangeStatusUseCaseImpl

    fun create(
        lifecycle: Lifecycle,
        providerFactory: BatteryChangeStatusProviderFactory
    ): GetObservableBatteryChangeStatusUseCaseImpl {
        val provider = providerFactory.create(lifecycle)
        return create(provider = provider)
    }
}