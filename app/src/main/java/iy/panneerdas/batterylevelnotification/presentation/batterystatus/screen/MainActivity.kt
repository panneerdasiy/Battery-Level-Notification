package iy.panneerdas.batterylevelnotification.presentation.batterystatus.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import iy.panneerdas.batterylevelnotification.domain.model.BatteryStatus
import iy.panneerdas.batterylevelnotification.presentation.batterystatus.viewmodel.BatteryStatusViewModel
import iy.panneerdas.batterylevelnotification.presentation.batterystatus.viewmodel.BatteryStatusViewModelFactory
import iy.panneerdas.batterylevelnotification.presentation.theme.BatteryLevelNotificationTheme
import iy.panneerdas.batterylevelnotification.presentation.util.NotificationPermissionManagerImpl

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<BatteryStatusViewModel> {
        BatteryStatusViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.permissionManager = NotificationPermissionManagerImpl(this@MainActivity)

        val batteryStatus = viewModel.batteryStatus
        setContent { BatteryStatusScreen(batteryStatus) }
    }

    @Composable
    fun BatteryStatusScreen(batteryStatus: BatteryStatus?) {
        BatteryLevelNotificationTheme {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Battery percent: ${batteryStatus?.percent?.toInt()}%")
                    Text("Charging: ${batteryStatus?.chargingStatus}")
                    Switch(
                        checked = viewModel.alertToggle.value!!,
                        onCheckedChange = viewModel::onAlertToggleChange
                    )
                }
            }
        }
    }
}