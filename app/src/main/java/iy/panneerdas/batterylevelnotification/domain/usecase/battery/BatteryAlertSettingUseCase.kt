package iy.panneerdas.batterylevelnotification.domain.usecase.battery

import iy.panneerdas.batterylevelnotification.domain.repository.BatteryAlertSettingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface BatteryAlertSettingUseCase {
    suspend fun setAlertEnableStatus(enable: Boolean)
    fun getAlertEnableStatus(): Flow<Boolean>
}

class BatteryAlertSettingUseCaseImpl @Inject constructor(
    private val settingRepository: BatteryAlertSettingRepository
) : BatteryAlertSettingUseCase {

    override suspend fun setAlertEnableStatus(enable: Boolean) =
        settingRepository.setAlertEnableStatus(enable)

    override fun getAlertEnableStatus(): Flow<Boolean> {
        return settingRepository.getAlertEnableStatus()
    }
}