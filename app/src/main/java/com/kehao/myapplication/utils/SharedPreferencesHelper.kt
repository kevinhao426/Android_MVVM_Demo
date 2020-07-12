package com.kehao.myapplication.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SharedPreferencesHelper {

    companion object {
        const val PERMISSION_CHECEKD = "permission_check"

        private var prefs: SharedPreferences? = null
        private lateinit var context: Context

        @Volatile
        private var instance: SharedPreferencesHelper? = null
        private val lock = Any()

        operator fun invoke(context: Context)
                : SharedPreferencesHelper = instance ?: synchronized(lock) {
            instance ?: buildHelper(context).also {
                instance = it
            }
        }

        private fun buildHelper(context: Context)
                : SharedPreferencesHelper {
            this.context = context
            prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPreferencesHelper()
        }

    }

    fun setPermissionAsked() {
        prefs?.edit(commit = true) {
            putBoolean(PERMISSION_CHECEKD, true)
        }
    }

    fun isPermissionAsked(): Boolean {
        return prefs?.getBoolean(PERMISSION_CHECEKD, false) ?: false

    }


    fun getDeviceToken() = EncryptUtil.getDeviceSerialNumber(context)
}