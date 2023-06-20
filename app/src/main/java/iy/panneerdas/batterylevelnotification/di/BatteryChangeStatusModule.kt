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
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryChangeStatusUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryChangeStatusUseCaseImpl
import iy.panneerdas.batterylevelnotification.platform.battery.BatteryChangeStatusProviderImpl

@InstallIn(ActivityComponent::class)
@Module
abstract class BatteryChangeStatusModule {
    companion object {
        @Provides
        fun provideBatteryChangeStatusProvider(
            @ActivityContext context: Context
        ): BatteryChangeStatusProvider {
            return BatteryChangeStatusProviderImpl(context = context as ComponentActivity)
        }
    }

    @Binds
    abstract fun bindBatteryChangeStatusUseCase(
        useCase: BatteryChangeStatusUseCaseImpl
    ): BatteryChangeStatusUseCase
}