package iy.panneerdas.batterylevelnotification.domain.usecase

import iy.panneerdas.batterylevelnotification.di.StopMonitorService
import iy.panneerdas.batterylevelnotification.di.StopMonitorWorker
import iy.panneerdas.batterylevelnotification.domain.platform.StopBatteryAlertServiceHandler
import javax.inject.Inject

interface StopBatteryAlertServiceUseCase {
    fun stop()
}

class StopBatteryAlertWorkerServiceUseCaseImpl @Inject constructor(
    @StopMonitorWorker private val handler: StopBatteryAlertServiceHandler
) : StopBatteryAlertServiceUseCase {

    override fun stop() = handler.stop()
}

class StopBatteryAlertServiceUseCaseImpl @Inject constructor(
    @StopMonitorService private val handler: StopBatteryAlertServiceHandler
) : StopBatteryAlertServiceUseCase {

    override fun stop() {
        handler.stop()
    }
}