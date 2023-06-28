package iy.panneerdas.batterylevelnotification.data.repository.workerlog

import iy.panneerdas.batterylevelnotification.data.model.DataWorkerLog
import iy.panneerdas.batterylevelnotification.domain.model.DomainWorkerLog
import iy.panneerdas.batterylevelnotification.domain.repository.WorkerLogRepository
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject

class WorkerLogRepositoryImpl @Inject constructor(private val dao: WorkerLogDao) :
    WorkerLogRepository {
    override fun insert(log: DataWorkerLog) = dao.insert(log)

    override fun getAll() = dao.getAll().map { logs ->
        logs.map {
            DomainWorkerLog(
                id = it.id,
                date = Date(it.timeMillis),
                batteryPercent = it.batteryPercent
            )
        }
    }
}