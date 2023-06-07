package iy.panneerdas.batterylevelnotification.presentation.util

import android.Manifest
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface NotificationPermissionManager {
    suspend fun requestPermission(): Boolean
}

class NotificationPermissionManagerImpl(private val activity: ComponentActivity) :
    NotificationPermissionManager {
    private val permission = Manifest.permission.POST_NOTIFICATIONS

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override suspend fun requestPermission(): Boolean {
        if (hasPermission()) return true

        return suspendCoroutine { continuation ->
            activity.registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                continuation.resume(isGranted)
            }.launch(permission)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun hasPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            permission
        ) == PermissionChecker.PERMISSION_GRANTED
    }
}