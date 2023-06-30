package iy.panneerdas.batterylevelnotification.domain.usecase

import iy.panneerdas.batterylevelnotification.di.MonitorService
import iy.panneerdas.batterylevelnotification.di.MonitorWorker
import iy.panneerdas.batterylevelnotification.domain.platform.BatteryAlertServiceHandler
import javax.inject.Inject

interface BatteryAlertServiceUseCase {
    fun scheduleWork()
    fun cancelWork()
}

class BatteryAlertWorkerServiceUseCaseImpl @Inject constructor(
    @MonitorWorker private val handler: BatteryAlertServiceHandler
) : BatteryAlertServiceUseCase {

    override fun scheduleWork() = handler.scheduleWork()
    override fun cancelWork() = handler.cancelWork()
}

class BatteryAlertServiceUseCaseImpl @Inject constructor(
    @MonitorService private val handler: BatteryAlertServiceHandler
) : BatteryAlertServiceUseCase {
    override fun scheduleWork() {
        handler.scheduleWork()
    }

    override fun cancelWork() {
        handler.cancelWork()
    }
}