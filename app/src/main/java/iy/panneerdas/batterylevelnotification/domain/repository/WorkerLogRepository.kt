package iy.panneerdas.batterylevelnotification.domain.repository

import iy.panneerdas.batterylevelnotification.data.model.DataWorkerLog
import iy.panneerdas.batterylevelnotification.domain.model.DomainWorkerLog
import kotlinx.coroutines.flow.Flow

interface WorkerLogRepository {
    fun insert(log: DataWorkerLog)
    fun getAll(): Flow<List<DomainWorkerLog>>
}