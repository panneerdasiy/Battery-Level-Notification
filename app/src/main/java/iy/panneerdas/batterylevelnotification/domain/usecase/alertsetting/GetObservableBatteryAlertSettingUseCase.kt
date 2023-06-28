package iy.panneerdas.batterylevelnotification.domain.usecase.alertsetting

import iy.panneerdas.batterylevelnotification.domain.repository.GetObservableBatteryAlertSettingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetObservableBatteryAlertSettingUseCase {
    operator fun invoke(): Flow<Boolean>
}

class GetObservableBatteryAlertSettingUseCaseImpl @Inject constructor(
    private val settingRepository: GetObservableBatteryAlertSettingRepository
) : GetObservableBatteryAlertSettingUseCase {

    override fun invoke() = settingRepository()
}