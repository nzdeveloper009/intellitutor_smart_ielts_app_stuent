package com.ielts.preparation

import android.app.Application
import android.content.Context
import android.content.SharedPreferences


class BaseClass :Application(){
    companion object{
        fun prefEditor(context: Context): SharedPreferences? {
            val sharedPreferences = context.getSharedPreferences("MySharedPref", MODE_PRIVATE)
            return sharedPreferences
        }
    }

}