package com.vinicalgaro.cidarte.data.local

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

@Singleton
class UserPreferences @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("cidarte_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_MEMBER_SINCE = "member_since_year"
    }

    fun getMemberSince(): String {
        val savedYear = prefs.getInt(KEY_MEMBER_SINCE, -1)

        return if (savedYear != -1) {
            savedYear.toString()
        } else {
            val currentYear = Calendar.getInstance().get(Calendar.YEAR)
            prefs.edit { putInt(KEY_MEMBER_SINCE, currentYear) }
            currentYear.toString()
        }
    }
}