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

interface StartChargeAlertEnableStatusDataStore {

    suspend fun setStartChargeAlertEnableStatus(enable: Boolean)

    suspend fun getStartChargeAlertEnableStatus(): Boolean
}

class StartChargeAlertEnableStatusDataStoreImpl @Inject constructor(
    @SmartChargeSettings private val dataStore: DataStore<Preferences>
) : StartChargeAlertEnableStatusDataStore {

    private val startAlertEnableStatusKey = booleanPreferencesKey("start_alert_enable_status_key")

    override suspend fun setStartChargeAlertEnableStatus(enable: Boolean) {
        dataStore.edit { it[startAlertEnableStatusKey] = enable }
    }

    override suspend fun getStartChargeAlertEnableStatus(): Boolean {
        return dataStore.data.map { it[startAlertEnableStatusKey] }.first() ?: false
    }

}