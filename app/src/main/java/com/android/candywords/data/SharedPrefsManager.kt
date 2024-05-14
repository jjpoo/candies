package com.android.candywords.data

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

const val CANDY_SHARED_PREFS = "candy words"
const val SETTINGS = "settings"
const val LEVELS = "levels"

object SharedPrefsManager {
    fun saveSettings(context: Context, settings: List<SettingOption>) {
        val settingsListString = Gson().toJson(settings)
        val prefs = context.getSharedPreferences(CANDY_SHARED_PREFS, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(SETTINGS, settingsListString)
        editor.apply()
    }

    fun getSettings(context: Context): List<SettingOption> {
        val prefs = context.getSharedPreferences(CANDY_SHARED_PREFS, Context.MODE_PRIVATE)
        val settings = prefs.getString(SETTINGS, "")
        Log.e("SAVED SETTINGS", "$settings")
        return Gson().fromJson(
            settings,
            object : TypeToken<List<SettingOption>>() {}.type
        )
    }

    fun saveLevelState(context: Context, levels: List<Level>) {
        val levelListString = Gson().toJson(levels)
        val prefs = context.getSharedPreferences(CANDY_SHARED_PREFS, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(LEVELS, levelListString)
        Log.e("SAVED SETTINGS", levelListString)
        editor.apply()
    }

    fun getLevelState(context: Context): List<Level> {
        val prefs = context.getSharedPreferences(CANDY_SHARED_PREFS, Context.MODE_PRIVATE)
        val levels = prefs.getString(LEVELS, "")
        Log.e("SAVED LEVELS", "$levels")
        return Gson().fromJson(
            levels,
            object : TypeToken<List<Level>>() {}.type
        )
    }
}