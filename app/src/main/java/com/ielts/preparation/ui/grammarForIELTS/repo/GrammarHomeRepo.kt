package com.ielts.preparation.ui.grammarForIELTS.repo

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.ielts.preparation.R
import com.ielts.preparation.ui.dashboard.data.DashboardItems

class GrammarHomeRepo (
    val context: Context,
){
    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("UseCompatLoadingForDrawables")
    fun getGrammarHomeItems(): List<DashboardItems> {
        return listOf(
            DashboardItems(
                context.getDrawable(R.drawable.ic_launcher_foreground),
                context.getString(R.string.partOfSpeechText),
               "",
                context.getColor(R.color.blue_500)
            ),
            DashboardItems(
                context.getDrawable(R.drawable.ic_launcher_foreground),
                context.getString(R.string.tenses),
               "",
                context.getColor(R.color.blue_500)
            )
        )
    }
}