package com.kehao.myapplication.utils

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.provider.Settings

fun isConnective(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val nw = connectivityManager.activeNetwork ?: return false
    val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
    return when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        else -> false
    }
}

fun askForInternet(context: Context) {
//        ToastUtil tu = new ToastUtil(context);
//        tu.show(applicationContext.getString(R.string.network_hint));


    val dialog = AlertDialog.Builder(context)
            .setTitle("Connection not available")
            .setMessage("Network connection error, please go to settings to check WI-FI connection")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                val i = Intent(Settings.ACTION_WIFI_SETTINGS)
                context.startActivity(i)
            }
            .setNegativeButton("CANCEL") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
    dialog.show()
}