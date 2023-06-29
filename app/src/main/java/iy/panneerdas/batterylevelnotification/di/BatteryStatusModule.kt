package iy.panneerdas.batterylevelnotification.di

import androidx.lifecycle.Lifecycle
import dagger.Binds
import dagger.Module
import dagger.assisted.AssistedFactory
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import iy.panneerdas.batterylevelnotification.domain.platform.BatteryStatusProvider
import iy.panneerdas.batterylevelnotification.domain.usecase.status.GetBatteryStatusUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.status.GetBatteryStatusUseCaseImpl
import iy.panneerdas.batterylevelnotification.domain.usecase.status.GetObservableBatteryChangeStatusUseCase
import iy.panneerdas.batterylevelnotification.platform.battery.BatteryStatusProviderImpl
import iy.panneerdas.batterylevelnotification.presentation.batterystatus.viewmodel.BatteryStatusViewModel

@InstallIn(SingletonComponent::class)
@Module
interface BatteryStatusModule {
    @Binds
    fun bindBatteryStatusUseCase(useCase: GetBatteryStatusUseCaseImpl): GetBatteryStatusUseCase

    @Binds
    fun bindBatteryStatusProvider(useCase: BatteryStatusProviderImpl): BatteryStatusProvider
}

@AssistedFactory
abstract class BatteryStatusViewModelFactory {
    abstract fun create(useCase: GetObservableBatteryChangeStatusUseCase): BatteryStatusViewModel

    fun create(
        lifecycle: Lifecycle,
        useCaseFactory: GetObservableBatteryChangeStatusUseCaseFactory,
        providerFactory: BatteryChangeStatusProviderFactory
    ): BatteryStatusViewModel {
        val useCase = useCaseFactory.create(
            lifecycle = lifecycle, providerFactory = providerFactory
        )
        return create(useCase = useCase)
    }
}