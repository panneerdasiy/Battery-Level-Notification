package iy.panneerdas.batterylevelnotification.data.repository.alertsetting

import iy.panneerdas.batterylevelnotification.domain.repository.GetObservableBatteryAlertSettingRepository
import javax.inject.Inject

class GetObservableBatteryAlertSettingRepositoryImpl @Inject constructor(
    private val dataStore: BatteryAlertSettingDataStore
) : GetObservableBatteryAlertSettingRepository {

    override fun invoke() = dataStore.getAlertEnableStatus()
}