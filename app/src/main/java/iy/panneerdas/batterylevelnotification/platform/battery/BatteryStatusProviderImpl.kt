package iy.panneerdas.batterylevelnotification.platform.battery

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import dagger.hilt.android.qualifiers.ApplicationContext
import iy.panneerdas.batterylevelnotification.domain.model.BatteryChargingStatus
import iy.panneerdas.batterylevelnotification.domain.model.BatteryStatus
import iy.panneerdas.batterylevelnotification.domain.platform.BatteryStatusProvider
import javax.inject.Inject

class BatteryStatusProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : BatteryStatusProvider {

    override fun invoke(): BatteryStatus? {
        val batteryIntent = getBatteryIntentOrNull() ?: return null

        val chargingStatus = getChargingStatus(batteryIntent)
        val batteryPercent = getBatteryPercent(batteryIntent)
        return BatteryStatus(percent = batteryPercent, chargingStatus = chargingStatus)
    }

    private fun getBatteryIntentOrNull(): Intent? {
        val batteryChangeIntentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        return context.registerReceiver(null, batteryChangeIntentFilter)
    }

    private fun getChargingStatus(batteryIntent: Intent): BatteryChargingStatus {
        return when (batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)) {
            BatteryManager.BATTERY_STATUS_CHARGING,
            BatteryManager.BATTERY_STATUS_FULL -> BatteryChargingStatus.CHARGING

            BatteryManager.BATTERY_STATUS_DISCHARGING,
            BatteryManager.BATTERY_STATUS_NOT_CHARGING -> BatteryChargingStatus.NOT_CHARGING

            else -> BatteryChargingStatus.UNKNOWN
        }
    }

    private fun getBatteryPercent(batteryIntent: Intent): Float {
        val level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)

        if (level == -1 || scale == -1) return -1f
        return level * 100 / scale.toFloat()
    }
}