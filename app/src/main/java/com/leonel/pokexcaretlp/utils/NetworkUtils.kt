package com.leonel.pokexcaretlp.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat.startActivity
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import android.provider.Settings

object NetworkUtils {
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else @Suppress("deprecation") {
            val activeNetwork = connectivityManager.activeNetworkInfo ?: return false
            return activeNetwork.isConnectedOrConnecting
        }
    }

    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET])
    fun isInternetReachable(context: Context): Boolean {
        if (isNetworkAvailable(context)) {
            //Network is available, check if internet is reachable
            try {
                val httpConnection: HttpURLConnection = URL("https://clients3.google.com/generate_204")
                    .openConnection() as HttpURLConnection
                httpConnection.setRequestProperty("User-Agent", "Android")
                httpConnection.setRequestProperty("Connection", "close")
                httpConnection.connectTimeout = 1500
                httpConnection.connect()

                return httpConnection.responseCode == 204
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return false
    }

    fun openInternetConnectivityPanel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val panelIntent = Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY)
            startActivity(context, panelIntent, null)
        }
    }
}