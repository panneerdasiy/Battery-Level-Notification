package iy.panneerdas.batterylevelnotification.data.repository.workerlog

import iy.panneerdas.batterylevelnotification.data.model.WorkerLog
import iy.panneerdas.batterylevelnotification.domain.repository.WorkerLogRepository

class WorkerLogRepositoryImpl(private val dao: WorkerLogDao) : WorkerLogRepository {
    override fun insert() = dao.insert(WorkerLog(timeMillis = System.currentTimeMillis()))

    override fun getAll() = dao.getAll()
}