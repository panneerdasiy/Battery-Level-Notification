package iy.panneerdas.batterylevelnotification.domain.repository

interface SetBatteryAlertSettingRepository {
    suspend operator fun invoke(enable: Boolean)
}