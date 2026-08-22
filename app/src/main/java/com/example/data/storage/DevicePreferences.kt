package com.example.data.storage

import android.content.Context
import android.content.SharedPreferences

class DevicePreferences(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "sultan_clock_prefs"
        private const val KEY_IP = "key_clock_ip"
        private const val KEY_USERNAME = "key_username"
        private const val KEY_PASSWORD = "key_password"
        private const val KEY_REMEMBER_PASS = "key_remember_pass"
        private const val KEY_AUTO_CONNECT = "key_auto_connect"
        private const val KEY_SAVED_IPS = "key_saved_ips"
        private const val KEY_LAST_CONN_TYPE = "key_last_conn_type"

        const val DEFAULT_MDNS_HOST = "sultanclock.local"
        const val DEFAULT_AP_IP = "192.168.4.1"
        const val DEFAULT_ADMIN_USER = "admin"
    }

    var ipAddress: String
        get() = prefs.getString(KEY_IP, DEFAULT_MDNS_HOST) ?: DEFAULT_MDNS_HOST
        set(value) = prefs.edit().putString(KEY_IP, value.trim()).apply()

    var username: String
        get() = prefs.getString(KEY_USERNAME, DEFAULT_ADMIN_USER) ?: DEFAULT_ADMIN_USER
        set(value) = prefs.edit().putString(KEY_USERNAME, value.trim()).apply()

    var password: String
        get() = prefs.getString(KEY_PASSWORD, "") ?: ""
        set(value) = prefs.edit().putString(KEY_PASSWORD, value).apply()

    var rememberPassword: Boolean
        get() = prefs.getBoolean(KEY_REMEMBER_PASS, true)
        set(value) = prefs.edit().putBoolean(KEY_REMEMBER_PASS, value).apply()

    var autoConnect: Boolean
        get() = prefs.getBoolean(KEY_AUTO_CONNECT, true)
        set(value) = prefs.edit().putBoolean(KEY_AUTO_CONNECT, value).apply()

    var lastConnectionType: String
        get() = prefs.getString(KEY_LAST_CONN_TYPE, "mDNS") ?: "mDNS"
        set(value) = prefs.edit().putString(KEY_LAST_CONN_TYPE, value).apply()

    fun getSavedIps(): Set<String> {
        val defaultSet = setOf(DEFAULT_MDNS_HOST, DEFAULT_AP_IP, "192.168.1.120")
        return prefs.getStringSet(KEY_SAVED_IPS, defaultSet) ?: defaultSet
    }

    fun addSavedIp(ip: String) {
        val current = getSavedIps().toMutableSet()
        current.add(ip.trim())
        prefs.edit().putStringSet(KEY_SAVED_IPS, current).apply()
    }

    fun removeSavedIp(ip: String) {
        val current = getSavedIps().toMutableSet()
        current.remove(ip.trim())
        prefs.edit().putStringSet(KEY_SAVED_IPS, current).apply()
    }

    fun clearAuth() {
        prefs.edit().remove(KEY_PASSWORD).apply()
    }
}
