package iy.panneerdas.batterylevelnotification.domain.repository

import iy.panneerdas.batterylevelnotification.data.model.WorkerLog
import kotlinx.coroutines.flow.Flow

interface WorkerLogRepository {
    fun insert(log: WorkerLog)
    fun getAll(): Flow<List<WorkerLog>>
}