package iy.panneerdas.batterylevelnotification.platform.battery

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.activity.ComponentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import iy.panneerdas.batterylevelnotification.domain.model.BatteryChargingStatus
import iy.panneerdas.batterylevelnotification.domain.model.BatteryStatus
import iy.panneerdas.batterylevelnotification.domain.platform.BatteryChangeStatusProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class BatteryChangeStatusProviderImpl(private val context: ComponentActivity) : BroadcastReceiver(),
    BatteryChangeStatusProvider, DefaultLifecycleObserver {
    private val _statusFlow = MutableSharedFlow<BatteryStatus>(replay = 1)

    init {
        context.lifecycle.addObserver(this)
    }

    override fun onStart(owner: LifecycleOwner) = register()

    override fun onStop(owner: LifecycleOwner) = unregister()

    private fun register() {
        context.registerReceiver(this, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }

    private fun unregister() {
        context.unregisterReceiver(this)
    }

    override fun invoke(): SharedFlow<BatteryStatus> = _statusFlow

    override fun onReceive(context: Context, intent: Intent) {
        CoroutineScope(Dispatchers.Default).launch {
            val chargingStatus = getChargingStatus(intent)
            val batteryPercent = getBatteryPercent(intent)
            val status = BatteryStatus(percent = batteryPercent, chargingStatus = chargingStatus)

            _statusFlow.emit(status)
        }
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