package com.ielts.preparation.ui.dashboard.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ielts.preparation.data.prefs.AppPrefs
import com.ielts.preparation.ui.dashboard.data.DashboardItems

class DashboardViewModel : ViewModel() {
    val dashboardItems : MutableList<DashboardItems> = mutableListOf()

    fun addItemsToDashboard(listOfItems : List<DashboardItems>){
        if (dashboardItems.isNullOrEmpty()){
            listOfItems.forEach {
                dashboardItems.add(it)
            }
        }
    }
    val pref = AppPrefs()
    fun saveTitle(context: Context, title:String) = pref.saveToolbarTitle(context,title)
    fun saveButtonVisibility(context: Context, isVisible:Boolean) = pref.saveButtonVisibility(context,isVisible)
}
