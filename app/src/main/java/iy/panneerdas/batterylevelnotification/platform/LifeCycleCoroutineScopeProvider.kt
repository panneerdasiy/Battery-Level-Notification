package iy.panneerdas.batterylevelnotification.platform

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

interface LifeCycleCoroutineScopeProvider {
    fun coroutineScope(): CoroutineScope
}

class LifeCycleCoroutineScopeProviderImpl @Inject constructor(
    private val lifecycle: Lifecycle
) : LifeCycleCoroutineScopeProvider {
    override fun coroutineScope() = lifecycle.coroutineScope
}