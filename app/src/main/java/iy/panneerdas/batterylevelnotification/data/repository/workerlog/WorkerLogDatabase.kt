package iy.panneerdas.batterylevelnotification.data.repository.workerlog

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import iy.panneerdas.batterylevelnotification.data.model.WorkerLog

@Database(
    entities = [WorkerLog::class],
    version = WorkerLogDatabase.VERSION,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
abstract class WorkerLogDatabase : RoomDatabase() {

    abstract fun workerLogDao(): WorkerLogDao

    companion object {
        const val VERSION = 2

        @Volatile
        private lateinit var INSTANCE: WorkerLogDatabase
        private const val DATABASE_NAME = "app_database"

        fun getInstance(context: Context): WorkerLogDatabase {
            synchronized(this) {
                if (::INSTANCE.isInitialized) return INSTANCE

                INSTANCE = Room.databaseBuilder(
                    context, WorkerLogDatabase::class.java, DATABASE_NAME
                ).build()
                return INSTANCE
            }
        }
    }
}