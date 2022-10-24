package com.ielts.preparation.ui.dashboard.repo

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import com.ielts.preparation.R
import com.ielts.preparation.ui.dashboard.data.DashboardItems

class MainDashboardItemsRepo(
    val context: Context
) {
    @RequiresApi(Build.VERSION_CODES.M)
    fun getDashboardItems(): List<DashboardItems> {
        return listOf(
            DashboardItems(
                getDrawable(context, R.drawable.ic_reading_lesson_card),
                context.getString(R.string.reading),
                context.getString(R.string.lesson),
                context.getColor(R.color.white)
            ),
            DashboardItems(
                getDrawable(context, R.drawable.ic_reading_lesson_card),
                context.getString(R.string.reading),
                context.getString(R.string.test),
                context.getColor(R.color.white),

            ),
            DashboardItems(
                getDrawable(context, R.drawable.writing),
                context.getString(R.string.writing),
                context.getString(R.string.lesson),
                context.getColor(R.color.white)
            ),
            DashboardItems(
                getDrawable(context, R.drawable.writing),
                context.getString(R.string.writing),
                context.getString(R.string.writing_task_1),
                context.getColor(R.color.white)
            ),
            DashboardItems(
                getDrawable(context, R.drawable.writing),
                context.getString(R.string.writing),
                context.getString(R.string.writing_task_2),
                context.getColor(R.color.white)
            ),
            DashboardItems(
                getDrawable(context, R.drawable.conversation),
                context.getString(R.string.speaking),
                context.getString(R.string.lesson),
                context.getColor(R.color.white)
            ),
            DashboardItems(
                getDrawable(context, R.drawable.conversation),
                context.getString(R.string.speaking),
                context.getString(R.string.test),
                context.getColor(R.color.white)
            ),
            DashboardItems(
                getDrawable(context, R.drawable.listening),
                context.getString(R.string.listening),
                context.getString(R.string.lesson),
                context.getColor(R.color.white)
            ),
            DashboardItems(
                getDrawable(context, R.drawable.listening),
                context.getString(R.string.listening),
                context.getString(R.string.test),
                context.getColor(R.color.white)
            )
        )
    }
}