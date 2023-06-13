package iy.panneerdas.batterylevelnotification.platform

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.CoroutineScope

interface LifeCycleCoroutineScopeProvider {
    fun coroutineScope(): CoroutineScope
}

class LifeCycleCoroutineScopeProviderImpl(private val lifecycle: Lifecycle) :
    LifeCycleCoroutineScopeProvider {
    override fun coroutineScope() = lifecycle.coroutineScope
}