package iy.panneerdas.batterylevelnotification.di

import android.content.Context
import androidx.activity.ComponentActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import iy.panneerdas.batterylevelnotification.domain.platform.BatteryChangeStatusProvider
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.status.GetObservableBatteryChangeStatusUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.status.GetObservableBatteryChangeStatusUseCaseImpl
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

    @Binds
    fun bindBatteryChangeStatusProvider(
        provider: BatteryChangeStatusProviderImpl
    ): BatteryChangeStatusProvider

    @Binds
    fun bindBatteryChangeStatusUseCase(
        useCase: GetObservableBatteryChangeStatusUseCaseImpl
    ): GetObservableBatteryChangeStatusUseCase
}