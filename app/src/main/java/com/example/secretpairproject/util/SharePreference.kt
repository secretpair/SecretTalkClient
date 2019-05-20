package com.example.secretpairproject.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager


object SharePreferenceManager {


    fun put(context: Context, key: String, value: String) {
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun put(context: Context, key: String, boolean: Boolean) {
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putBoolean(key, boolean)
        editor.apply()
    }

    fun put(context: Context, key: String, int: Int) {
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putInt(key, int)
        editor.apply()
    }


    fun getString(context: Context, key: String): String {
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        return pref.getString(key, " ")

    }

    fun getBoolean(context: Context, key: String): Boolean {
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        return pref.getBoolean(key, false)
    }

    fun getInt(context: Context, key: String): Int {
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        return pref.getInt(key, 0)
    }
}