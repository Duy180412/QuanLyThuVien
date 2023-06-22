package com.example.qltvkotlin.feature.helper

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

suspend fun <T> launch2(block: suspend CoroutineScope.() -> T) =
    withContext(Dispatchers.IO) {
        block()
    }

fun CoroutineScope.launchAndCancel(block: suspend () -> Unit): Job {
    val job = launch { block() }
    job.invokeOnCompletion { cause ->
        if (cause is CancellationException) {
            job.children.forEach { it.cancel() }
        }
    }
    return job
}

fun <T> CoroutineScope.launchAndCancel(
    block: suspend () -> Deferred<T>,
    onSuccess: (T) -> Unit,
    onError: (Throwable) -> Unit = {}
): Job {
    val job = launch {
        try {
            val result = block().await()
            onSuccess(result)
        } catch (e: Exception) {
            onError(e)
        }
    }
    job.invokeOnCompletion { cause ->
        if (cause is CancellationException) {
            job.children.forEach { it.cancel() }
        }
    }
    return job
}

@Suppress("UNREACHABLE_CODE")
suspend fun <T> launchAndReturnBoolean(block: suspend CoroutineScope.() -> T): Boolean {
    return try {
        val result = coroutineScope(block)
        if (result is Boolean) {
            return result
        }
        if (result is Long) {
            return result > 0
        }
        return true
    } catch (ex: Exception) {
        ex.printStackTrace()
        return false
    }
}