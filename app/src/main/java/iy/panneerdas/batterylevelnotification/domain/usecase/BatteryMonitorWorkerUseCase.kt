package iy.panneerdas.batterylevelnotification.domain.usecase

import iy.panneerdas.batterylevelnotification.platform.BatteryMonitorWorkHandler

interface BatteryMonitorWorkerUseCase {
    operator fun invoke()
}

class BatteryMonitorWorkerUseCaseImpl(private val handler: BatteryMonitorWorkHandler) :
    BatteryMonitorWorkerUseCase {

    override fun invoke() = handler()
}