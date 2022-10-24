package com.ielts.preparation.data.prefs

import android.content.Context
import android.content.SharedPreferences
import com.ielts.preparation.BaseClass
import com.ielts.preparation.R
import com.ielts.preparation.data.prefs.utils.SharedPreferenceLiveData

class AppPrefs() {

    private val toolbarTitle= "toolbar_title"
    private val isVisible = "isVisible"

    fun saveToolbarTitle(context: Context,value:String){
     BaseClass.prefEditor(context)?.edit()?.putString(toolbarTitle, value)?.apply()
    }
    fun getToolbarTITLE(context: Context): SharedPreferenceLiveData<String>? {
        return   BaseClass.prefEditor(context)?.liveData(toolbarTitle, context.getString(R.string.ielts_vocabulary_actionBar_text))
    }


    fun saveButtonVisibility(context: Context,value:Boolean){
        BaseClass.prefEditor(context)?.edit()?.putBoolean(isVisible, value)?.apply()
    }
    fun getButtonVisibility(context: Context): SharedPreferenceLiveData<Boolean>? {
        return BaseClass.prefEditor(context)?.liveData(isVisible,true)
    }


    private inline fun <reified T> SharedPreferences.liveData(
        key: String,
        default: T
    ): SharedPreferenceLiveData<T> {
        @Suppress("UNCHECKED_CAST")
        return object : SharedPreferenceLiveData<T>(this, key, default) {
            override fun getValueFromPreferences(key: String, defValue: T): T {
                return when (default) {
                    is String -> getString(key, default) as T
                    is Int -> getInt(key, default) as T
                    is Long -> getLong(key, default) as T
                    is Boolean -> getBoolean(key, default) as T
                    is Float -> getFloat(key, default) as T
                    is Set<*> -> getStringSet(key, default as Set<String>) as T
                    is MutableSet<*> -> getStringSet(key, default as MutableSet<String>) as T
                    else -> throw IllegalArgumentException("generic type not handled")
                }
            }
        }
    }

}