package iy.panneerdas.batterylevelnotification.domain.model

import java.util.Date

data class DomainWorkerLog(val id: Int, val date: Date, val batteryPercent: Float)
