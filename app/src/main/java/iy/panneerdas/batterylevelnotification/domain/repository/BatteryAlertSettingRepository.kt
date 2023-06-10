package iy.panneerdas.batterylevelnotification.domain.repository

import kotlinx.coroutines.flow.Flow

interface BatteryAlertSettingRepository {
    suspend fun setAlertEnableStatus(enable: Boolean)
    fun getAlertEnableStatus(): Flow<Boolean>
}