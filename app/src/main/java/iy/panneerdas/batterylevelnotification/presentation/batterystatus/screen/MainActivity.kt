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
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import iy.panneerdas.batterylevelnotification.presentation.batterystatus.viewmodel.BatteryStatusViewModel
import iy.panneerdas.batterylevelnotification.presentation.batterystatus.viewmodel.BatteryStatusViewModelFactory
import iy.panneerdas.batterylevelnotification.presentation.theme.BatteryLevelNotificationTheme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<BatteryStatusViewModel> {
        BatteryStatusViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val batteryStatus = viewModel.batteryStatus
        setContent {
            BatteryLevelNotificationTheme {
                // A surface container using the 'background' color from the theme
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
                    }
                }
            }
        }
    }
}