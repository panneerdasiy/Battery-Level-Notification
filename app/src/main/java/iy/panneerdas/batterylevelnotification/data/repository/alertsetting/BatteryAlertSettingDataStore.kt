package iy.panneerdas.batterylevelnotification.data.repository.alertsetting

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "battery_alert_setting")

interface BatteryAlertSettingDataStore{

    suspend fun setAlertEnableStatus(enable: Boolean)
    fun getAlertEnableStatus(): Flow<Boolean>
}

class BatteryAlertSettingDataStoreImpl @Inject constructor(
    @ApplicationContext private val context: Context
): BatteryAlertSettingDataStore {

    private val alertEnableKey = booleanPreferencesKey("alert_enable_key")

    override suspend fun setAlertEnableStatus(enable: Boolean) {
        context.dataStore.edit { setting ->
            setting[alertEnableKey] = enable
        }
    }

    override fun getAlertEnableStatus(): Flow<Boolean> {
        return context.dataStore.data.map { setting ->
            setting[alertEnableKey] ?: false
        }
    }
}