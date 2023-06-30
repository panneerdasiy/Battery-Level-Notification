package iy.panneerdas.batterylevelnotification.domain.usecase

import iy.panneerdas.batterylevelnotification.di.MonitorService
import iy.panneerdas.batterylevelnotification.di.MonitorWorker
import iy.panneerdas.batterylevelnotification.domain.platform.BatteryAlertServiceHandler
import javax.inject.Inject

interface BatteryMonitorWorkerUseCase {
    fun scheduleWork()
    fun cancelWork()
}

class BatteryMonitorWorkerUseCaseImpl @Inject constructor(
    @MonitorWorker private val handler: BatteryAlertServiceHandler
) : BatteryMonitorWorkerUseCase {

    override fun scheduleWork() = handler.scheduleWork()
    override fun cancelWork() = handler.cancelWork()
}

class BatteryMonitorServiceUseCaseImpl @Inject constructor(
    @MonitorService private val handler: BatteryAlertServiceHandler
) : BatteryMonitorWorkerUseCase {
    override fun scheduleWork() {
        handler.scheduleWork()
    }

    override fun cancelWork() {
        handler.cancelWork()
    }
}