package iy.panneerdas.batterylevelnotification.domain.usecase.worker

import iy.panneerdas.batterylevelnotification.domain.model.DomainWorkerLog
import iy.panneerdas.batterylevelnotification.domain.repository.WorkerLogRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetAllWorkerLogUseCase {
    operator fun invoke(): Flow<List<DomainWorkerLog>>
}

class GetAllWorkerLogUseCaseImpl @Inject constructor(
    private val repo: WorkerLogRepository
) : GetAllWorkerLogUseCase {

    override operator fun invoke() = repo.getAll()
}