package iy.panneerdas.batterylevelnotification.domain.platform

interface BatteryMonitorWorkHandler {
    fun scheduleWork()
    fun cancelWork()
}