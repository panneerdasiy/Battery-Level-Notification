package iy.panneerdas.batterylevelnotification.data.repository.alertsetting

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import iy.panneerdas.batterylevelnotification.di.SmartChargeSettings
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface StopChargeAlertEnableStatusDataStore {

    suspend fun setStopChargeAlertEnableStatus(enable: Boolean)

    suspend fun getStopChargeAlertEnableStatus(): Boolean
}

class StopChargeAlertEnableStatusDataStoreImpl @Inject constructor(
    @SmartChargeSettings private val dataStore: DataStore<Preferences>
) : StopChargeAlertEnableStatusDataStore {

    private val stopAlertEnableStatusKey = booleanPreferencesKey("stop_alert_enable_status_key")

    override suspend fun setStopChargeAlertEnableStatus(enable: Boolean) {
        dataStore.edit { it[stopAlertEnableStatusKey] = enable }
    }

    override suspend fun getStopChargeAlertEnableStatus(): Boolean {
        return dataStore.data.map { it[stopAlertEnableStatusKey] }.first() ?: false
    }
}