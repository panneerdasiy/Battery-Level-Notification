package iy.panneerdas.batterylevelnotification.presentation.batterystatus.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.coroutineScope
import dagger.hilt.android.AndroidEntryPoint
import iy.panneerdas.batterylevelnotification.R
import iy.panneerdas.batterylevelnotification.presentation.batterystatus.model.DisplayBatteryStatus
import iy.panneerdas.batterylevelnotification.presentation.batterystatus.model.DisplayWorkerLog
import iy.panneerdas.batterylevelnotification.presentation.batterystatus.viewmodel.BatteryStatusViewModel
import iy.panneerdas.batterylevelnotification.presentation.theme.BatteryLevelNotificationTheme
import iy.panneerdas.batterylevelnotification.presentation.util.NotificationPermissionManager
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var permissionManager: NotificationPermissionManager

    @Inject
    lateinit var viewModel: BatteryStatusViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPermissionManager()

        setContent {
            val workerLogs = viewModel.logsFlow.collectAsState(
                initial = emptyList()
            ).value.reversed()
            val batteryStatus = viewModel.batteryStatus.collectAsState(
                initial = DisplayBatteryStatus(
                    percent = -1,
                    chargingStatus = getString(R.string.unknown)
                )
            ).value
            val isAlertEnabled = viewModel.isAlertEnabledFlow.collectAsState(false).value

            BatteryStatusScreen(
                batteryStatus = batteryStatus,
                displayWorkerLogs = workerLogs,
                isAlertEnabled = isAlertEnabled
            )
        }
    }

    private fun initPermissionManager() {
        lifecycle.coroutineScope.launch {
            viewModel.requestPermissionFlow.collect {
                viewModel.onPermissionResult(
                    granted = permissionManager.requestPermission()
                )
            }
        }
    }

    @Composable
    fun BatteryStatusScreen(
        batteryStatus: DisplayBatteryStatus?,
        displayWorkerLogs: List<DisplayWorkerLog>,
        isAlertEnabled: Boolean
    ) {

        BatteryLevelNotificationTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Battery percent: ${batteryStatus?.percent}%")
                    Text("Charging: ${batteryStatus?.chargingStatus}")
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Smart Charging Remainders")
                        Box(Modifier.width(10.dp))
                        Switch(
                            checked = isAlertEnabled,
                            onCheckedChange = viewModel::onAlertToggleChange,
                        )
                    }
                    Box(Modifier.height(16.dp))
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(displayWorkerLogs) { log ->
                            Column {
                                Text("${log.id}: ${log.dateTime}")
                                Text("Battery Status: ${log.batteryPercent}")
                            }
                        }
                    }
                }
            }
        }
    }
}