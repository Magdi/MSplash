package com.magdi.msplash.usecase

import com.magdi.msplash.utils.Results
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {
    operator fun invoke(params: P): Flow<Results<R>> = execute(params)
        .catch { cause -> emit(Results.Error(cause.message)) }
        .flowOn(coroutineDispatcher)

    protected abstract fun execute(params: P): Flow<Results<R>>
}