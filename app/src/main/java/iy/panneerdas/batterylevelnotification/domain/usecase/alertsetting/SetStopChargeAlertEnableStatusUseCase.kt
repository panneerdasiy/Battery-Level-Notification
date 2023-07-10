package iy.panneerdas.batterylevelnotification.domain.usecase.alertsetting

import iy.panneerdas.batterylevelnotification.data.repository.alertsetting.StopChargeAlertEnableStatusDataStore
import iy.panneerdas.batterylevelnotification.data.repository.alertsetting.StopChargeAlertEnableStatusDataStoreImpl
import javax.inject.Inject

interface SetStopChargeAlertEnableStatusUseCase {
    suspend operator fun invoke(enable: Boolean)
}

class SetStopChargeAlertEnableStatusUseCaseImpl @Inject constructor(
    private val dataStore: StopChargeAlertEnableStatusDataStore
): SetStopChargeAlertEnableStatusUseCase{

    override suspend fun invoke(enable: Boolean) {
        dataStore.setStopChargeAlertEnableStatus(enable = enable)
    }

}