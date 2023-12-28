package com.example.read.common.data.base

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.read.common.utils.AppDispatchers
import com.example.read.common.domain.utils.Either
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseRepository(private val appDispatchers: AppDispatchers) {

    protected fun <ValueDto : Any, Value : Any> doPagingFlowRequest(
        pagingSource: PagingSource<ValueDto, Value>,
        pageSize: Int = 20,
        initialLoadSize: Int = pageSize,
        prefetchDistance: Int = 2 * pageSize,
        maxSize: Int = pageSize + (10 * prefetchDistance),
        enablePlaceholders: Boolean = true,
        jumpThreshold: Int = Int.MIN_VALUE
    ): Flow<PagingData<Value>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                prefetchDistance = prefetchDistance,
                initialLoadSize = initialLoadSize,
                maxSize = maxSize,
                enablePlaceholders = enablePlaceholders,
                jumpThreshold = jumpThreshold
            ),
            pagingSourceFactory = { pagingSource }
        ).flow.flowOn(appDispatchers.default)
    }

    protected fun <F, T> doRequest(
        map: ((it: F) -> T)? = null,
        request: suspend () -> F
    ) = flow<Either<String, T>> {
        emit(Either.Right(map?.let { invoke ->
            invoke(request())
        }))
    }.flowOn(appDispatchers.io).catch {
        emit(Either.Left(it.localizedMessage))
    }
}