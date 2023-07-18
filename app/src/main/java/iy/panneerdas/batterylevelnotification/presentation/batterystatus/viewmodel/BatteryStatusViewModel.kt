package iy.panneerdas.batterylevelnotification.presentation.batterystatus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import iy.panneerdas.batterylevelnotification.R
import iy.panneerdas.batterylevelnotification.di.StartAlertService
import iy.panneerdas.batterylevelnotification.di.StopAlertService
import iy.panneerdas.batterylevelnotification.domain.model.BatteryChargingStatus
import iy.panneerdas.batterylevelnotification.domain.model.BatteryStatus
import iy.panneerdas.batterylevelnotification.domain.usecase.alertservice.StartBatteryAlertServiceUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.alertservice.StopBatteryAlertServiceUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.alertsetting.GetObservableBatteryAlertSettingUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.alertsetting.SetBatteryAlertSettingUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.worker.GetAllWorkerLogUseCase
import iy.panneerdas.batterylevelnotification.platform.I18nStringProvider
import iy.panneerdas.batterylevelnotification.presentation.batterystatus.model.DisplayBatteryStatus
import iy.panneerdas.batterylevelnotification.presentation.batterystatus.model.DisplayWorkerLog
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class BatteryStatusViewModel @Inject constructor(
    private val i18nStringProvider: I18nStringProvider,
    @StartAlertService val startBatteryAlertServiceUseCase: StartBatteryAlertServiceUseCase,
    @StopAlertService val stopBatteryAlertServiceUseCase: StopBatteryAlertServiceUseCase,
    private val setBatteryAlertSettingUseCase: SetBatteryAlertSettingUseCase,
    getObservableBatteryAlertSettingUseCase: GetObservableBatteryAlertSettingUseCase,
    getAllWorkerLogUseCase: GetAllWorkerLogUseCase,
) : ViewModel() {
    private val _requestPermissionFlow = MutableSharedFlow<Unit>()
    val requestPermissionFlow: SharedFlow<Unit>
        get() = _requestPermissionFlow

    private val _batteryStatus = MutableStateFlow<DisplayBatteryStatus>(
        DisplayBatteryStatus(
            percent = -1,
            chargingStatus = i18nStringProvider.getString(R.string.unknown)
        )
    )
    val batteryStatus: SharedFlow<DisplayBatteryStatus>
        get() = _batteryStatus

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

    fun onBatteryStatusChange(it: BatteryStatus) {
        viewModelScope.launch {
            _batteryStatus.emit(
                DisplayBatteryStatus(
                    percent = it.percent.toInt(),
                    chargingStatus = getDisplayChargingStatus(it.chargingStatus)
                )
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
            _requestPermissionFlow.emit(Unit)
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
            startBatteryAlertServiceUseCase.start()
            setBatteryAlertSettingUseCase.invoke(enable = true)
        }
    }

    private fun disableAlert() {
        viewModelScope.launch {
            stopBatteryAlertServiceUseCase.stop()
            setBatteryAlertSettingUseCase.invoke(enable = false)
        }
    }
}