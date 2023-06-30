package iy.panneerdas.batterylevelnotification.domain.platform

interface BatteryAlertServiceHandler {
    fun scheduleWork()
    fun cancelWork()
}