package iy.panneerdas.batterylevelnotification.domain.platform

import iy.panneerdas.batterylevelnotification.domain.model.BatteryStatus
import kotlinx.coroutines.flow.SharedFlow

interface BatteryChangeStatusProvider {
    operator fun invoke(): SharedFlow<BatteryStatus>
}