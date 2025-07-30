package com.berlin.aflami.viewmodel.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

abstract class BaseViewModel<S, E>(
    initialState: S
) : ViewModel() {
    protected val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    protected val _effect = MutableSharedFlow<E>()
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
            }  catch (e: NetworkException) {
                onError(NetworkErrorState(e.message.toString()))
            }  catch (e: NotFoundException) {
                onError(ErrorUiState(e.message.toString()))
            } catch (e: ServerException) {
                onError(ErrorUiState(e.message.toString()))
            } catch (e: Exception) {
                onError(ErrorUiState(e.message.toString()))
            }
        }
    }

    protected fun updateState(updater: (S) -> S) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update(updater)
        }
    }

    protected fun sendNewEffect(newEffect: E) {
        viewModelScope.launch() {
            _effect.emit(newEffect)
        }
    }
}