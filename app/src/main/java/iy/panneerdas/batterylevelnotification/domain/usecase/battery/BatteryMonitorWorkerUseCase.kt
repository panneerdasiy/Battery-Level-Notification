package iy.panneerdas.batterylevelnotification.domain.usecase.battery

import iy.panneerdas.batterylevelnotification.domain.platform.BatteryMonitorWorkHandler
import javax.inject.Inject

interface BatteryMonitorWorkerUseCase {
    fun scheduleWork()
    fun cancelWork()
}

class BatteryMonitorWorkerUseCaseImpl @Inject constructor(
    private val handler: BatteryMonitorWorkHandler
) : BatteryMonitorWorkerUseCase {

    override fun scheduleWork() = handler.scheduleWork()
    override fun cancelWork() = handler.cancelWork()
}