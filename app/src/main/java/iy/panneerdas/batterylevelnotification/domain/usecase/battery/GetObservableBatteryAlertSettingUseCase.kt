package iy.panneerdas.batterylevelnotification.domain.usecase.battery

import iy.panneerdas.batterylevelnotification.domain.repository.BatteryAlertSettingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetObservableBatteryAlertSettingUseCase {
    operator fun invoke(): Flow<Boolean>
}

class GetObservableBatteryAlertSettingUseCaseImpl @Inject constructor(
    private val settingRepository: BatteryAlertSettingRepository
) : GetObservableBatteryAlertSettingUseCase {

    override fun invoke(): Flow<Boolean> {
        return settingRepository.getAlertEnableStatus()
    }
}