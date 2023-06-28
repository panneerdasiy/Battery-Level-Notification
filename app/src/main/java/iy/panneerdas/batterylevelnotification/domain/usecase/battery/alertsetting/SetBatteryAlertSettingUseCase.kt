package iy.panneerdas.batterylevelnotification.domain.usecase.battery.alertsetting

import iy.panneerdas.batterylevelnotification.domain.repository.SetBatteryAlertSettingRepository
import javax.inject.Inject

interface SetBatteryAlertSettingUseCase {
    suspend fun invoke(enable: Boolean)
}

class SetBatteryAlertSettingUseCaseImpl @Inject constructor(
    private val settingRepository: SetBatteryAlertSettingRepository
) : SetBatteryAlertSettingUseCase {

    override suspend fun invoke(enable: Boolean) = settingRepository(enable = enable)
}