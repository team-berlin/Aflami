package com.berlin.safeimageviewer

import kotlinx.coroutines.flow.Flow


interface NetworkConnectivityObserver {
    fun observe(): Flow<NetworkStatus>
}