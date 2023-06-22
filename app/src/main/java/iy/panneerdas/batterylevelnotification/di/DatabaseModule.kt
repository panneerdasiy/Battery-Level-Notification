package iy.panneerdas.batterylevelnotification.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import iy.panneerdas.batterylevelnotification.data.repository.workerlog.AppDatabase
import iy.panneerdas.batterylevelnotification.data.repository.workerlog.WorkerLogDao
import iy.panneerdas.batterylevelnotification.data.repository.workerlog.WorkerLogRepositoryImpl
import iy.panneerdas.batterylevelnotification.domain.repository.WorkerLogRepository
import iy.panneerdas.batterylevelnotification.domain.usecase.worker.GetAllWorkerLogUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.worker.GetAllWorkerLogUseCaseImpl
import iy.panneerdas.batterylevelnotification.domain.usecase.worker.InsertWorkerLogUseCase
import iy.panneerdas.batterylevelnotification.domain.usecase.worker.InsertWorkerLogUseCaseImpl

@InstallIn(SingletonComponent::class)
@Module
abstract class DatabaseModule {

    companion object {
        @Provides
        fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
            return AppDatabase.getInstance(context = context)
        }

        @Provides
        fun provideWorkerLogDao(appDatabase: AppDatabase): WorkerLogDao {
            return appDatabase.workerLogDao()
        }
    }

    @Binds
    abstract fun bindInsertWorkerLogUseCase(
        useCase: InsertWorkerLogUseCaseImpl
    ): InsertWorkerLogUseCase

    @Binds
    abstract fun bindGetAllWorkerLogUseCase(
        useCase: GetAllWorkerLogUseCaseImpl
    ): GetAllWorkerLogUseCase

    @Binds
    abstract fun bindWorkerLogRepository(repo: WorkerLogRepositoryImpl): WorkerLogRepository
}