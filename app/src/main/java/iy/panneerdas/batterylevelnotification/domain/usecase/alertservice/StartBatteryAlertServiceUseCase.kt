package iy.panneerdas.batterylevelnotification.domain.usecase.alertservice

import iy.panneerdas.batterylevelnotification.di.StartAlertService
import iy.panneerdas.batterylevelnotification.di.StartAlertWorker
import iy.panneerdas.batterylevelnotification.domain.platform.StartBatteryAlertServiceHandler
import javax.inject.Inject

interface StartBatteryAlertServiceUseCase {
    fun start()
}

class StartBatteryAlertWorkerServiceUseCaseImpl @Inject constructor(
    @StartAlertWorker private val handler: StartBatteryAlertServiceHandler
) : StartBatteryAlertServiceUseCase {

    override fun start() = handler.start()
}

class StartBatteryAlertServiceUseCaseImpl @Inject constructor(
    @StartAlertService private val handler: StartBatteryAlertServiceHandler
) : StartBatteryAlertServiceUseCase {
    override fun start() {
        handler.start()
    }
}