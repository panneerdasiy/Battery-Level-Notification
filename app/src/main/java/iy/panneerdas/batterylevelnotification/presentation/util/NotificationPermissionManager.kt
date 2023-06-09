package iy.panneerdas.batterylevelnotification.presentation.util

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface NotificationPermissionManager {
    suspend fun requestPermission(): Boolean
}

class NotificationPermissionManagerImpl(private val activity: ComponentActivity) :
    NotificationPermissionManager {
    @SuppressLint("InlinedApi")
    private val notificationPermission = Manifest.permission.POST_NOTIFICATIONS

    private lateinit var requestPermissionContinuation: Continuation<Boolean>
    private val permissionRequestLauncher = activity.registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) {
        requestPermissionContinuation.resume(it)
    }

    override suspend fun requestPermission(): Boolean {
        if (hasPermission()) return true

        return suspendCoroutine { continuation ->
            this.requestPermissionContinuation = continuation
            permissionRequestLauncher.launch(notificationPermission)
        }
    }

    private fun hasPermission(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return true

        return ContextCompat.checkSelfPermission(
            activity,
            notificationPermission
        ) == PermissionChecker.PERMISSION_GRANTED
    }
}