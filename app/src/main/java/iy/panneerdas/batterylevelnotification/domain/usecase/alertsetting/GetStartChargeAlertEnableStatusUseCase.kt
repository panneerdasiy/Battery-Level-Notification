package iy.panneerdas.batterylevelnotification.domain.usecase.alertsetting

import iy.panneerdas.batterylevelnotification.data.repository.alertsetting.StartChargeAlertEnableStatusDataStore
import javax.inject.Inject

interface GetStartChargeAlertEnableStatusUseCase {
    suspend operator fun invoke(): Boolean
}

class GetStartChargeAlertEnableStatusUseCaseImpl @Inject constructor(
    private val startChargeAlertEnableStatusDataStore: StartChargeAlertEnableStatusDataStore
) : GetStartChargeAlertEnableStatusUseCase {

    override suspend fun invoke(): Boolean {
        return startChargeAlertEnableStatusDataStore.getStartChargeAlertEnableStatus()
    }

}