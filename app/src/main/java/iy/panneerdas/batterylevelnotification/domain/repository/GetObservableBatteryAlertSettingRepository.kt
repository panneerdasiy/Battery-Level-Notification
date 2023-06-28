package iy.panneerdas.batterylevelnotification.domain.repository

import kotlinx.coroutines.flow.Flow

interface GetObservableBatteryAlertSettingRepository {
    operator fun invoke(): Flow<Boolean>
}