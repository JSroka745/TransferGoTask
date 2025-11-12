package com.example.currencyconverter.listeners

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities

interface NetworkStatusListener {
    fun onNetworkStatusChanged(isConnected: Boolean)
}

class NetworkConnectivityObserver(private val context: Context) {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private var listener: NetworkStatusListener? = null

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            listener?.onNetworkStatusChanged(true)
        }

        override fun onLost(network: Network) {
            listener?.onNetworkStatusChanged(false)
        }

        override fun onUnavailable() {
            listener?.onNetworkStatusChanged(false)
        }
    }

    fun startListening(networkStatusListener: NetworkStatusListener) {
        listener = networkStatusListener
        listener?.onNetworkStatusChanged(isCurrentlyConnected())
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    fun stopListening() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
        listener = null
    }

    private fun isCurrentlyConnected(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}