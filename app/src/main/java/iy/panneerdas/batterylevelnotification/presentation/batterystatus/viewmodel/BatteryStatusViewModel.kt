package iy.panneerdas.batterylevelnotification.presentation.batterystatus.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import iy.panneerdas.batterylevelnotification.R
import iy.panneerdas.batterylevelnotification.common.LifecycleViewModel
import iy.panneerdas.batterylevelnotification.di.BatteryChangeStatusProviderFactory
import iy.panneerdas.batterylevelnotification.di.StartAlertService
import iy.panneerdas.batterylevelnotification.di.StopAlertService
import iy.panneerdas.batterylevelnotification.domain.model.BatteryChargingStatus
import iy.panneerdas.batterylevelnotification.domain.usecase.alertservice.StartBatteryAlertServiceUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.alertservice.StopBatteryAlertServiceUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.alertsetting.GetObservableBatteryAlertSettingUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.alertsetting.SetBatteryAlertSettingUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.status.GetObservableBatteryChangeStatusUseCaseImpl
import iy.panneerdas.batterylevelnotification.domain.usecase.worker.GetAllWorkerLogUseCase
import iy.panneerdas.batterylevelnotification.platform.I18nStringProvider
import iy.panneerdas.batterylevelnotification.presentation.batterystatus.model.DisplayBatteryStatus
import iy.panneerdas.batterylevelnotification.presentation.batterystatus.model.DisplayWorkerLog
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
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
    getObservableBatteryChangeStatusUseCaseFactory: GetObservableBatteryChangeStatusUseCaseImpl.GetObservableBatteryChangeStatusUseCaseFactory,
    batteryChangeStatusProviderFactory: BatteryChangeStatusProviderFactory
) : LifecycleViewModel() {

    private val getObservableBatteryChangeStatusUseCase =
        getObservableBatteryChangeStatusUseCaseFactory.create(
            provider = batteryChangeStatusProviderFactory.create(
                lifecycle = lifecycle
            )
        )

    private val _requestPermissionFlow = MutableSharedFlow<Unit>()
    val requestPermissionFlow: SharedFlow<Unit>
        get() = _requestPermissionFlow

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