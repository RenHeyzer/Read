package com.example.read.utils.extensions

import com.google.android.gms.tasks.CancellationTokenSource
import com.parse.boltsinternal.Task
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

public suspend fun <T> Task<T>.await(): T =
    awaitImpl(null)

private suspend fun <T> Task<T>.awaitImpl(cancellationTokenSource: CancellationTokenSource?): T {
    // fast path
    if (isCompleted) {
        val e = error
        return if (e == null) {
            if (isCancelled) {
                throw CancellationException("Task $this was cancelled normally.")
            } else {
                result as T
            }
        } else {
            throw e
        }
    }

    return suspendCancellableCoroutine { cont ->
        // Run the callback directly to avoid unnecessarily scheduling on the main thread.
        val e = error
        if (e == null) {
            if (isCancelled) cont.cancel() else cont.resume(result as T)
        } else {
            cont.resumeWithException(e)
        }

        if (cancellationTokenSource != null) {
            cont.invokeOnCancellation {
                cancellationTokenSource.cancel()
            }
        }
    }
}

private object DirectExecutor : Executor {

    override fun execute(r: Runnable) {
        r.run()
    }
}