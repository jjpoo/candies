package com.android.candywords.connectivity

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun observe(): Flow<Status>
    fun getCurrentNetworkState(): Status
    enum class Status {
        Available, Lost, Unavailable
    }
}