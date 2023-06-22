package iy.panneerdas.batterylevelnotification.domain.usecase.worker

import iy.panneerdas.batterylevelnotification.data.model.WorkerLog
import iy.panneerdas.batterylevelnotification.domain.repository.WorkerLogRepository
import javax.inject.Inject

interface InsertWorkerLogUseCase {
    fun insert(log: WorkerLog)
}

class InsertWorkerLogUseCaseImpl @Inject constructor(
    private val repo: WorkerLogRepository
) : InsertWorkerLogUseCase {

    override fun insert(log: WorkerLog) = repo.insert(log = log)
}