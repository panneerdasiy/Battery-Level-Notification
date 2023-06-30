package iy.panneerdas.batterylevelnotification.domain.usecase.alertservice

import iy.panneerdas.batterylevelnotification.di.StartMonitorService
import iy.panneerdas.batterylevelnotification.di.StartMonitorWorker
import iy.panneerdas.batterylevelnotification.domain.platform.StartBatteryAlertServiceHandler
import javax.inject.Inject

interface StartBatteryAlertServiceUseCase {
    fun start()
}

class StartBatteryAlertWorkerServiceUseCaseImpl @Inject constructor(
    @StartMonitorWorker private val handler: StartBatteryAlertServiceHandler
) : StartBatteryAlertServiceUseCase {

    override fun start() = handler.start()
}

class StartBatteryAlertServiceUseCaseImpl @Inject constructor(
    @StartMonitorService private val handler: StartBatteryAlertServiceHandler
) : StartBatteryAlertServiceUseCase {
    override fun start() {
        handler.start()
    }
}