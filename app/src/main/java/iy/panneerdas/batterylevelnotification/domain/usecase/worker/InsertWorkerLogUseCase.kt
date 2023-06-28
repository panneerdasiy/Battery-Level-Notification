package iy.panneerdas.batterylevelnotification.domain.usecase.worker

import iy.panneerdas.batterylevelnotification.data.model.DataWorkerLog
import iy.panneerdas.batterylevelnotification.domain.repository.WorkerLogRepository
import javax.inject.Inject

interface InsertWorkerLogUseCase {
    fun insert(log: DataWorkerLog)
}

class InsertWorkerLogUseCaseImpl @Inject constructor(
    private val repo: WorkerLogRepository
) : InsertWorkerLogUseCase {

    override fun insert(log: DataWorkerLog) = repo.insert(log = log)
}