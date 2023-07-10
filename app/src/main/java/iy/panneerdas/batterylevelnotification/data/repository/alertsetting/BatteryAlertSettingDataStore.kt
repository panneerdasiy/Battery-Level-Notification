package iy.panneerdas.batterylevelnotification.data.repository.alertsetting

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import iy.panneerdas.batterylevelnotification.di.SmartChargeSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface BatteryAlertSettingDataStore {

    suspend fun setAlertEnableStatus(enable: Boolean)
    fun getAlertEnableStatus(): Flow<Boolean>
}

class BatteryAlertSettingDataStoreImpl @Inject constructor(
    @SmartChargeSettings private val dataStore: DataStore<Preferences>
) : BatteryAlertSettingDataStore {

    private val alertEnableKey = booleanPreferencesKey("alert_enable_key")

    override suspend fun setAlertEnableStatus(enable: Boolean) {
        dataStore.edit { setting ->
            setting[alertEnableKey] = enable
        }
    }

    override fun getAlertEnableStatus(): Flow<Boolean> {
        return dataStore.data.map { setting ->
            setting[alertEnableKey] ?: false
        }
    }
}