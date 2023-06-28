package iy.panneerdas.batterylevelnotification.di

import android.content.Context
import android.content.res.Resources
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import iy.panneerdas.batterylevelnotification.platform.I18nStringProvider
import iy.panneerdas.batterylevelnotification.platform.I18nStringProviderImpl

@InstallIn(SingletonComponent::class)
@Module
interface PlatformWrapperApplicationModule {

    companion object {
        @Provides
        fun provideResources(@ApplicationContext context: Context): Resources {
            return context.resources
        }
    }

    @Binds
    fun providesI18nStringProvider(providerImpl: I18nStringProviderImpl): I18nStringProvider
}