package com.gf.horoscopo.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.ContextCompat.getString
import androidx.core.content.edit
import com.gf.horoscopo.R

class SessionManager(val context: Context) {

    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(getString(context, R.string.SESSION_PREFERENCES_KEY),
                Context.MODE_PRIVATE)

    fun setFavorite(id: String) {
        sharedPreferences.edit {
            putString(getString(context, R.string.FAVORITE_KEY), id)
        }
    }

    fun getFavorite(): String? {
        return sharedPreferences.getString(getString(context, R.string.FAVORITE_KEY), null)
    }

    fun isFavorite(id: String): Boolean {
        return getFavorite() == id
    }
}