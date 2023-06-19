package iy.panneerdas.batterylevelnotification.domain.usecase.worker

import iy.panneerdas.batterylevelnotification.data.model.WorkerLog
import iy.panneerdas.batterylevelnotification.domain.repository.WorkerLogRepository
import kotlinx.coroutines.flow.Flow

interface WorkerLogUseCase {
    fun getAll(): Flow<List<WorkerLog>>
    fun insert(log: WorkerLog)
}

class WorkerLogUseCaseImpl(private val repo: WorkerLogRepository) : WorkerLogUseCase {
    override fun getAll(): Flow<List<WorkerLog>> = repo.getAll()

    override fun insert(log: WorkerLog) = repo.insert(log = log)
}