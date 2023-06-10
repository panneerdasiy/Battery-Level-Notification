package iy.panneerdas.batterylevelnotification.domain.usecase.battery

import iy.panneerdas.batterylevelnotification.domain.repository.BatteryAlertSettingRepository
import kotlinx.coroutines.flow.Flow

interface BatteryAlertSettingUseCase {
    suspend fun setAlertEnableStatus(enable: Boolean)
    fun getAlertEnableStatus(): Flow<Boolean>
}

class BatteryAlertSettingUseCaseImpl(private val settingRepository: BatteryAlertSettingRepository) :
    BatteryAlertSettingUseCase {

    override suspend fun setAlertEnableStatus(enable: Boolean) =
        settingRepository.setAlertEnableStatus(enable)

    override fun getAlertEnableStatus(): Flow<Boolean> {
        return settingRepository.getAlertEnableStatus()
    }
}