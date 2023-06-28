package iy.panneerdas.batterylevelnotification.domain.usecase.battery

import iy.panneerdas.batterylevelnotification.domain.repository.BatteryAlertSettingRepository
import javax.inject.Inject

interface SetBatteryAlertSettingUseCase {
    suspend fun invoke(enable: Boolean)
}

class SetBatteryAlertSettingUseCaseImpl @Inject constructor(
    private val settingRepository: BatteryAlertSettingRepository
) : SetBatteryAlertSettingUseCase {

    override suspend fun invoke(enable: Boolean) =
        settingRepository.setAlertEnableStatus(enable)
}