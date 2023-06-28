package iy.panneerdas.batterylevelnotification.data.repository.workerlog

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import iy.panneerdas.batterylevelnotification.data.model.DataWorkerLog
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkerLogDao {

    @Insert
    fun insert(log: DataWorkerLog)

    @Query("SELECT * FROM worker_log")
    fun getAll(): Flow<List<DataWorkerLog>>
}