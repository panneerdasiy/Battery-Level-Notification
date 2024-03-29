package iy.panneerdas.batterylevelnotification.di

import android.content.Context
import androidx.activity.ComponentActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import iy.panneerdas.batterylevelnotification.presentation.util.NotificationPermissionManager
import iy.panneerdas.batterylevelnotification.presentation.util.NotificationPermissionManagerImpl

@InstallIn(ActivityComponent::class)
@Module
class PlatformWrapperActivityModule {

    @Provides
    fun providePermissionManager(
        @ActivityContext context: Context
    ): NotificationPermissionManager {
        return NotificationPermissionManagerImpl(activity = context as ComponentActivity)
    }
}