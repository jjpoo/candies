package com.android.candywords.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.util.Log
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class ConnectivityObserverImpl(
    private val context: Context
) : ConnectivityObserver {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun getCurrentNetworkState(): ConnectivityObserver.Status {
        val network =
            connectivityManager.activeNetwork ?: return ConnectivityObserver.Status.Unavailable

        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            ?: return ConnectivityObserver.Status.Unavailable

        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> ConnectivityObserver.Status.Available
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> ConnectivityObserver.Status.Available
            else -> ConnectivityObserver.Status.Unavailable
        }
    }

    override fun observe(): Flow<ConnectivityObserver.Status> {
        return callbackFlow {
            Log.e("ConnectivityObserverImpl", "Starting network observation")

            val callback = object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch {
                        send(ConnectivityObserver.Status.Available)
                        Log.e("ConnectivityObserverImpl", "Network Available")
                    }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch {
                        send(ConnectivityObserver.Status.Lost)
                        Log.e("ConnectivityObserverImpl", "Network Lost")
                    }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch {
                        send(ConnectivityObserver.Status.Unavailable)
                        Log.e("ConnectivityObserverImpl", "Network Unavailable")
                    }
                }
            }

            connectivityManager.registerDefaultNetworkCallback(callback)
            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
    }
}