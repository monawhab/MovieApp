package com.movieapp.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class InternetInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val isConnectedToInternet = checkInternetConnectivity()

        if (!isConnectedToInternet) {
            throw IOException("No internet connectivity")
        }

        return chain.proceed(request)
    }

    private fun checkInternetConnectivity(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}