package iy.panneerdas.batterylevelnotification.domain.usecase.alertsetting

import iy.panneerdas.batterylevelnotification.data.repository.alertsetting.StopChargeAlertEnableStatusDataStore
import javax.inject.Inject

interface GetStopChargeAlertEnableStatusUseCase {
    suspend operator fun invoke(): Boolean
}

class GetStopChargeAlertEnableStatusUseCaseImpl @Inject constructor(
    private val dataStore: StopChargeAlertEnableStatusDataStore
) : GetStopChargeAlertEnableStatusUseCase {

    override suspend fun invoke(): Boolean {
        return dataStore.getStopChargeAlertEnableStatus()
    }

}