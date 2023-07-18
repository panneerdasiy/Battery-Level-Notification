package iy.panneerdas.batterylevelnotification.platform.battery

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import iy.panneerdas.batterylevelnotification.domain.model.BatteryChargingStatus
import iy.panneerdas.batterylevelnotification.domain.model.BatteryStatus
import iy.panneerdas.batterylevelnotification.domain.platform.BatteryChangeStatusProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class BatteryChangeStatusProviderImpl @AssistedInject constructor(
    @ApplicationContext private val context: Context,
    @Assisted lifecycle: Lifecycle,
) : BroadcastReceiver(), BatteryChangeStatusProvider, DefaultLifecycleObserver {

    @AssistedFactory
    interface BatteryChangeStatusProviderFactory {
        fun create(lifecycle: Lifecycle): BatteryChangeStatusProviderImpl
    }

    private val _statusFlow = MutableSharedFlow<BatteryStatus>(replay = 1)

    init {
        lifecycle.addObserver(this)
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