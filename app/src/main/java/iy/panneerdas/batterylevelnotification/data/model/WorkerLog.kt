package iy.panneerdas.batterylevelnotification.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "worker_log")
data class WorkerLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "time_millis") val timeMillis: Long
)
