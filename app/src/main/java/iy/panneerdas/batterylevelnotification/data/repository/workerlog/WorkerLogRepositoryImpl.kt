package iy.panneerdas.batterylevelnotification.data.repository.workerlog

import iy.panneerdas.batterylevelnotification.data.model.WorkerLog
import iy.panneerdas.batterylevelnotification.domain.repository.WorkerLogRepository
import javax.inject.Inject

class WorkerLogRepositoryImpl @Inject constructor(private val dao: WorkerLogDao) :
    WorkerLogRepository {
    override fun insert(log: WorkerLog) = dao.insert(log)

    override fun getAll() = dao.getAll()
}