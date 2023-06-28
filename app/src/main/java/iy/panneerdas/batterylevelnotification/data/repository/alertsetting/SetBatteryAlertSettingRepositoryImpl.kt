package iy.panneerdas.batterylevelnotification.data.repository.alertsetting

import iy.panneerdas.batterylevelnotification.domain.repository.SetBatteryAlertSettingRepository
import javax.inject.Inject

class SetBatteryAlertSettingRepositoryImpl @Inject constructor(
    private val dataStore: BatteryAlertSettingDataStore
) : SetBatteryAlertSettingRepository {

    override suspend operator fun invoke(enable: Boolean) {
        dataStore.setAlertEnableStatus(enable = enable)
    }
}