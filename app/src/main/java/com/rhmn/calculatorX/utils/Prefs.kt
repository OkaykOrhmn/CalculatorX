package com.rhmn.calculatorX.utils

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("data",Context.MODE_PRIVATE)
    private val APP_PREF_INT_EXAMPLE = "mathsData"

    var mathsData: String?
        get() = preferences.getString(APP_PREF_INT_EXAMPLE, "null")
        set(value) = preferences.edit().putString(APP_PREF_INT_EXAMPLE, value).apply()

}