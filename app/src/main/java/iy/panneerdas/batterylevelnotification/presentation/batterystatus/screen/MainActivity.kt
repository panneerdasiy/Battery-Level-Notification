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
import androidx.work.WorkManager
import iy.panneerdas.batterylevelnotification.data.repository.BatteryAlertSettingRepositoryImpl
import iy.panneerdas.batterylevelnotification.data.repository.workerlog.AppDatabase
import iy.panneerdas.batterylevelnotification.data.repository.workerlog.WorkerLogRepositoryImpl
import iy.panneerdas.batterylevelnotification.domain.model.BatteryStatus
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryAlertSettingUseCaseImpl
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryChangeStatusUseCaseImpl
import iy.panneerdas.batterylevelnotification.domain.usecase.battery.BatteryMonitorWorkerUseCaseImpl
import iy.panneerdas.batterylevelnotification.domain.usecase.worker.WorkerLogUseCaseImpl
import iy.panneerdas.batterylevelnotification.platform.battery.BatteryChangeStatusProviderImpl
import iy.panneerdas.batterylevelnotification.platform.worker.BatteryMonitorWorkHandlerImpl
import iy.panneerdas.batterylevelnotification.presentation.batterystatus.model.WorkerLog
import iy.panneerdas.batterylevelnotification.presentation.batterystatus.viewmodel.BatteryStatusViewModel
import iy.panneerdas.batterylevelnotification.presentation.batterystatus.viewmodel.BatteryStatusViewModelFactory
import iy.panneerdas.batterylevelnotification.presentation.theme.BatteryLevelNotificationTheme
import iy.panneerdas.batterylevelnotification.presentation.util.NotificationPermissionManagerImpl
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<BatteryStatusViewModel> {
        val workManager = WorkManager.getInstance(this@MainActivity)
        val handler = BatteryMonitorWorkHandlerImpl(workManager = workManager)
        val batteryMonitorWorkerUseCase = BatteryMonitorWorkerUseCaseImpl(handler = handler)

        val settingRepository = BatteryAlertSettingRepositoryImpl(this@MainActivity)
        val batteryAlertSettingUseCase = BatteryAlertSettingUseCaseImpl(settingRepository)

        val appDatabase = AppDatabase.getInstance(this@MainActivity)
        val workerLogDao = appDatabase.workerLogDao()
        val workerLogRepo = WorkerLogRepositoryImpl(workerLogDao)
        val workerLogUseCase = WorkerLogUseCaseImpl(workerLogRepo)

        val provider =
            BatteryChangeStatusProviderImpl(this)//TODO make sure on activity change view model gets latest ref
        val batteryChangeStatusUseCase = BatteryChangeStatusUseCaseImpl(provider)

        BatteryStatusViewModelFactory(
            batteryMonitorWorkerUseCase = batteryMonitorWorkerUseCase,
            batteryAlertSettingUseCase = batteryAlertSettingUseCase,
            batteryChangeStatusUseCase = batteryChangeStatusUseCase,
            workerLogUseCase = workerLogUseCase,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPermissionManager()

        setContent {
            val workerLogs = viewModel.logsFlow.collectAsState(
                initial = emptyList()
            ).value.reversed()
            val batteryStatus = viewModel.batteryStatus.collectAsState(
                initial = BatteryStatus()
            ).value
            val isAlertEnabled = viewModel.isAlertEnabledFlow.collectAsState(false).value

            BatteryStatusScreen(
                batteryStatus = batteryStatus,
                workerLogs = workerLogs,
                isAlertEnabled = isAlertEnabled
            )
        }
    }

    private fun initPermissionManager() {
        val permissionManager = NotificationPermissionManagerImpl(
            this@MainActivity
        )
        lifecycle.coroutineScope.launch {
            viewModel.requestPermissionState.collect {
                viewModel.onPermissionResult(
                    granted = permissionManager.requestPermission()
                )
            }
        }
    }

    @Composable
    fun BatteryStatusScreen(
        batteryStatus: BatteryStatus?,
        workerLogs: List<WorkerLog>,
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
                    Text("Battery percent: ${batteryStatus?.percent?.toInt()}%")
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
                    LazyColumn {
                        items(workerLogs) { log ->
                            Text("${log.id}: ${log.dateTime}")
                        }
                    }
                }
            }
        }
    }
}