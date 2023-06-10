package iy.panneerdas.batterylevelnotification.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import iy.panneerdas.batterylevelnotification.domain.repository.BatteryAlertSettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "battery_alert_setting")

class BatteryAlertSettingRepositoryImpl(private val context: Context) :
    BatteryAlertSettingRepository {

    private val alertEnableKey = booleanPreferencesKey("alert_enable_key")

    override suspend fun setAlertEnableStatus(enable: Boolean) {
        context.dataStore.edit { setting ->
            setting[alertEnableKey] = enable
        }
    }

    override fun getAlertEnableStatus(): Flow<Boolean> {
        return context.dataStore.data.map { setting -> setting[alertEnableKey] ?: false }
    }
}