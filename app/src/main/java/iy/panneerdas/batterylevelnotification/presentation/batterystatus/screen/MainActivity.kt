package iy.panneerdas.batterylevelnotification.presentation.batterystatus.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.work.WorkManager
import iy.panneerdas.batterylevelnotification.domain.model.BatteryStatus
import iy.panneerdas.batterylevelnotification.domain.usecase.BatteryMonitorWorkerUseCaseImpl
import iy.panneerdas.batterylevelnotification.domain.usecase.BatteryStatusUseCaseImpl
import iy.panneerdas.batterylevelnotification.platform.BatteryMonitorWorkHandlerImpl
import iy.panneerdas.batterylevelnotification.platform.BatteryStatusProviderImpl
import iy.panneerdas.batterylevelnotification.presentation.batterystatus.viewmodel.BatteryStatusViewModel
import iy.panneerdas.batterylevelnotification.presentation.batterystatus.viewmodel.BatteryStatusViewModelFactory
import iy.panneerdas.batterylevelnotification.presentation.theme.BatteryLevelNotificationTheme
import iy.panneerdas.batterylevelnotification.presentation.util.NotificationPermissionManagerImpl

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<BatteryStatusViewModel> {
        val batteryStatusProvider = BatteryStatusProviderImpl(this@MainActivity)
        val batteryStatusUseCase = BatteryStatusUseCaseImpl(batteryStatusProvider)

        val workManager = WorkManager.getInstance(this@MainActivity)
        val handler = BatteryMonitorWorkHandlerImpl(workManager = workManager)
        val batteryMonitorWorkerUseCase = BatteryMonitorWorkerUseCaseImpl(handler = handler)

        BatteryStatusViewModelFactory(
            batteryStatusUseCase = batteryStatusUseCase,
            batteryMonitorWorkerUseCase = batteryMonitorWorkerUseCase,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.permissionManager = NotificationPermissionManagerImpl(this)

        val batteryStatus = viewModel.batteryStatus
        setContent { BatteryStatusScreen(batteryStatus) }
    }

    @Composable
    fun BatteryStatusScreen(batteryStatus: BatteryStatus?) {
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
                    Text("Battery percent: ${batteryStatus?.percent?.toInt()}%")
                    Text("Charging: ${batteryStatus?.chargingStatus}")
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Smart Charging Remainders")
                        Box(Modifier.width(10.dp))
                        Switch(
                            checked = viewModel.alertToggle.observeAsState(false).value,
                            onCheckedChange = viewModel::onAlertToggleChange,
                        )
                    }
                }
            }
        }
    }
}