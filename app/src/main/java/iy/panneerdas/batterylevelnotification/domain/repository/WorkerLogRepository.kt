package iy.panneerdas.batterylevelnotification.domain.repository

import iy.panneerdas.batterylevelnotification.data.model.WorkerLog
import iy.panneerdas.batterylevelnotification.domain.model.DomainWorkerLog
import kotlinx.coroutines.flow.Flow

interface WorkerLogRepository {
    fun insert(log: WorkerLog)
    fun getAll(): Flow<List<DomainWorkerLog>>
}