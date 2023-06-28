package iy.panneerdas.batterylevelnotification.data.repository.alertsetting

import iy.panneerdas.batterylevelnotification.domain.repository.BatteryAlertSettingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BatteryAlertSettingRepositoryImpl @Inject constructor(
    private val dataStore: BatteryAlertSettingDataStore
) : BatteryAlertSettingRepository {

    override suspend fun setAlertEnableStatus(enable: Boolean) {
        dataStore.setAlertEnableStatus(enable = enable)
    }

    override fun getAlertEnableStatus(): Flow<Boolean> {
        return dataStore.getAlertEnableStatus()
    }
}