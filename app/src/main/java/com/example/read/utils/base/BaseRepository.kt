package com.example.read.utils.base

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow

abstract class BaseRepository {

    protected fun <ValueDto : Any, Value : Any> doPagingFlowRequest(
        pagingSource: PagingSource<ValueDto, Value>,
        pageSize: Int = 20,
        initialLoadSize: Int = pageSize * 2,
    ): Flow<PagingData<Value>> {
        return Pager(
            config = PagingConfig(
                pageSize,
                initialLoadSize,
            ),
            pagingSourceFactory = { pagingSource }
        ).flow
    }
}