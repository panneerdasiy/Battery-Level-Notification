package iy.panneerdas.batterylevelnotification.presentation.batterystatus.viewmodel

import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import iy.panneerdas.batterylevelnotification.R
import iy.panneerdas.batterylevelnotification.di.MonitorService
import iy.panneerdas.batterylevelnotification.domain.model.BatteryChargingStatus
import iy.panneerdas.batterylevelnotification.domain.usecase.BatteryAlertServiceUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.alertsetting.GetObservableBatteryAlertSettingUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.alertsetting.SetBatteryAlertSettingUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.status.GetObservableBatteryChangeStatusUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.worker.GetAllWorkerLogUseCase
import iy.panneerdas.batterylevelnotification.platform.I18nStringProvider
import iy.panneerdas.batterylevelnotification.platform.LifeCycleCoroutineScopeProvider
import iy.panneerdas.batterylevelnotification.presentation.batterystatus.model.DisplayBatteryStatus
import iy.panneerdas.batterylevelnotification.presentation.batterystatus.model.DisplayWorkerLog
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class BatteryStatusViewModel @AssistedInject constructor(
    private val i18nStringProvider: I18nStringProvider,
    @MonitorService val batteryAlertServiceUseCase: BatteryAlertServiceUseCase,
    private val setBatteryAlertSettingUseCase: SetBatteryAlertSettingUseCase,
    getObservableBatteryAlertSettingUseCase: GetObservableBatteryAlertSettingUseCase,
    lifecycleCoroutineProvider: LifeCycleCoroutineScopeProvider,
    @Assisted getObservableBatteryChangeStatusUseCase: GetObservableBatteryChangeStatusUseCase,
    getAllWorkerLogUseCase: GetAllWorkerLogUseCase,
) {
    private val viewModelScope = lifecycleCoroutineProvider.coroutineScope()

    val requestPermissionState = MutableSharedFlow<Unit>()
    val batteryStatus = getObservableBatteryChangeStatusUseCase().distinctUntilChanged().map {
        DisplayBatteryStatus(
            percent = it.percent.toInt(),
            chargingStatus = getDisplayChargingStatus(it.chargingStatus)
        )
    }

    private fun getDisplayChargingStatus(chargingStatus: BatteryChargingStatus): String {
        return when (chargingStatus) {
            BatteryChargingStatus.CHARGING -> i18nStringProvider.getString(R.string.charging)
            BatteryChargingStatus.NOT_CHARGING -> i18nStringProvider.getString(R.string.not_charging)
            BatteryChargingStatus.UNKNOWN -> i18nStringProvider.getString(R.string.unknown)
        }
    }

    val isAlertEnabledFlow = getObservableBatteryAlertSettingUseCase()

    val logsFlow = getAllWorkerLogUseCase()
        .map { logs ->
            logs.map { log ->
                val formatter = SimpleDateFormat("dd/MM/yy hh:mm a", Locale.getDefault())
                DisplayWorkerLog(
                    id = log.id,
                    dateTime = formatter.format(log.date),
                    batteryPercent = log.batteryPercent
                )
            }
        }

    fun onAlertToggleChange(isChecked: Boolean) {
        viewModelScope.launch {
            setBatteryAlertSettingUseCase.invoke(enable = isChecked)

            if (isChecked) {
                onAlertToggleEnable()
                return@launch
            }

            disableAlert()
        }
    }

    private fun onAlertToggleEnable() {
        viewModelScope.launch {
            requestPermissionState.emit(Unit)
        }
    }

    fun onPermissionResult(granted: Boolean) {
        if (granted) {
            enableAlert()
            return
        }

        disableAlert()
    }

    private fun enableAlert() {
        viewModelScope.launch {
            batteryAlertServiceUseCase.start()
            setBatteryAlertSettingUseCase.invoke(enable = true)
        }
    }

    private fun disableAlert() {
        viewModelScope.launch {
            batteryAlertServiceUseCase.stop()
            setBatteryAlertSettingUseCase.invoke(enable = false)
        }
    }
}