package com.berlin.aflami.viewmodel.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import com.berlin.aflami.viewmodel.base.BasePagingSource.Companion.ENABLE_PLACEHOLDERS
import com.berlin.aflami.viewmodel.base.BasePagingSource.Companion.INITIAL_LOAD_SIZE
import com.berlin.aflami.viewmodel.base.BasePagingSource.Companion.PAGE_SIZE
import com.berlin.aflami.viewmodel.base.BasePagingSource.Companion.PREFETCH_DISTANCE
import com.berlin.exception.AlreadyExistsException
import com.berlin.exception.NetworkException
import com.berlin.exception.NotFoundException
import com.berlin.exception.ServerException
import com.berlin.exception.UnauthorizedException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<SCREEN_STATE, SCREEN_EFFECT>(
    initialState: SCREEN_STATE,
) : ViewModel() {
    protected val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    protected val _effect = MutableSharedFlow<SCREEN_EFFECT>()
    val effect = _effect.asSharedFlow()

    protected fun <T> tryToCall(
        call: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (error: ErrorUiState) -> Unit,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) {
        viewModelScope.launch(dispatcher) {
            try {
                val result = call()
                onSuccess(result)
            } catch (e: UnauthorizedException) {
                onError(InvalidationErrorState(e.message.toString()))
            } catch (e: NetworkException) {
                onError(NetworkErrorState(e.message.toString()))
            } catch (e: NotFoundException) {
                onError(ErrorUiState(e.message.toString()))
            } catch (e: ServerException) {
                onError(ErrorUiState(e.message.toString()))
            } catch (e: AlreadyExistsException) {
                onError(MovieAlreadyExistInList(e.message.toString()))
            } catch (e: Exception) {
                onError(ErrorUiState(e.message.toString()))
            }
        }
    }

    protected fun defaultPageConfigurations(
        pageSize: Int = PAGE_SIZE,
        initialLoadSize: Int = INITIAL_LOAD_SIZE,
        prefetchDistance: Int = PREFETCH_DISTANCE,
        enablePlaceholders: Boolean = ENABLE_PLACEHOLDERS,
    ) = PagingConfig(
        pageSize = pageSize,
        initialLoadSize = initialLoadSize,
        prefetchDistance = prefetchDistance,
        enablePlaceholders = enablePlaceholders
    )

    protected fun updateState(updater: (SCREEN_STATE) -> SCREEN_STATE) = _state.update(updater)

    protected fun sendNewEffect(newEffect: SCREEN_EFFECT) {
        viewModelScope.launch() {
            _effect.emit(newEffect)
        }
    }
}