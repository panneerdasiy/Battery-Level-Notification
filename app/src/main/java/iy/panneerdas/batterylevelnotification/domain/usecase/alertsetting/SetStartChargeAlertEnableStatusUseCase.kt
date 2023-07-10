package iy.panneerdas.batterylevelnotification.domain.usecase.alertsetting

import iy.panneerdas.batterylevelnotification.data.repository.alertsetting.StartChargeAlertEnableStatusDataStore
import javax.inject.Inject

interface SetStartChargeAlertEnableStatusUseCase {
    suspend operator fun invoke(enable: Boolean)
}

class SetStartChargeAlertEnableStatusUseCaseImpl @Inject constructor(
    private val startChargeAlertEnableStatusDataStore: StartChargeAlertEnableStatusDataStore
) : SetStartChargeAlertEnableStatusUseCase {

    override suspend fun invoke(enable: Boolean) {
        startChargeAlertEnableStatusDataStore.setStartChargeAlertEnableStatus(enable = enable)
    }

}