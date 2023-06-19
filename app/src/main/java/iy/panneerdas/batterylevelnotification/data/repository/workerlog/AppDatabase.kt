package iy.panneerdas.batterylevelnotification.data.repository.workerlog

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import iy.panneerdas.batterylevelnotification.data.model.WorkerLog

@Database(
    entities = [WorkerLog::class],
    version = AppDatabase.VERSION,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun workerLogDao(): WorkerLogDao

    companion object {
        const val VERSION = 2

        @Volatile
        private lateinit var INSTANCE: AppDatabase
        private const val DATABASE_NAME = "app_database"

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                if (::INSTANCE.isInitialized) return INSTANCE

                INSTANCE = Room.databaseBuilder(
                    context, AppDatabase::class.java, DATABASE_NAME
                ).build()
                return INSTANCE
            }
        }
    }
}