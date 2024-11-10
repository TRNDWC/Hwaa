package com.example.hwaa.presentation.util

import android.content.Context
import android.content.SharedPreferences
import com.example.hwaa.data.model.UserModel
import com.google.gson.Gson

object UserProvider {

    private const val PREFERENCES_FILE_NAME = "user_prefs"
    private const val KEY_USER = "key_user"
    private lateinit var preferences: SharedPreferences

    private val gson = Gson()

    fun init(context: Context) {
        preferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    }

    fun saveUser(user: UserModel) {
        val userJson = gson.toJson(user)
        preferences.edit().putString(KEY_USER, userJson).apply()
    }

    fun getUser(): UserModel? {
        val userJson = preferences.getString(KEY_USER, null)
        return userJson?.let {
            gson.fromJson(it, UserModel::class.java)
        }
    }

    fun clearUserData() {
        preferences.edit().remove(KEY_USER).apply()
    }
}
