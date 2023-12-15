package com.example.read.feature_profile.domain.extensions

import com.example.read.utils.state_holders.Either
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

suspend fun <L, R> Flow<Either<L, R>>.handleLatestAsEitherFlow(
    success: suspend (data: R) -> Unit,
    error: suspend (message: L) -> Unit
) {
    collectLatest {
        it.eitherResult(
            success = success,
            error = error
        )
    }
}

suspend fun <L, R> Flow<Either<L, R>>.handleAsEitherFlow(
    success: suspend (data: R) -> Unit,
    error: suspend (message: L) -> Unit
) {
    collectLatest {
        it.eitherResult(
            success = success,
            error = error
        )
    }
}

suspend fun <L, R> Either<L, R>.eitherResult(
    success: suspend (data: R) -> Unit,
    error: suspend (message: L) -> Unit
) {
    when (this) {
        is Either.Left -> {
            this.error?.let { message ->
                error(message)
            }
        }

        is Either.Right -> {
            data?.let { data ->
                success(data)
            }
        }
    }
}