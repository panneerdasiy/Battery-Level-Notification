package iy.panneerdas.batterylevelnotification.domain.usecase.alertservice

import iy.panneerdas.batterylevelnotification.di.StopAlertService
import iy.panneerdas.batterylevelnotification.di.StopAlertWorker
import iy.panneerdas.batterylevelnotification.domain.platform.StopBatteryAlertServiceHandler
import javax.inject.Inject

interface StopBatteryAlertServiceUseCase {
    fun stop()
}

class StopBatteryAlertWorkerServiceUseCaseImpl @Inject constructor(
    @StopAlertWorker private val handler: StopBatteryAlertServiceHandler
) : StopBatteryAlertServiceUseCase {

    override fun stop() = handler.stop()
}

class StopBatteryAlertServiceUseCaseImpl @Inject constructor(
    @StopAlertService private val handler: StopBatteryAlertServiceHandler
) : StopBatteryAlertServiceUseCase {

    override fun stop() {
        handler.stop()
    }
}