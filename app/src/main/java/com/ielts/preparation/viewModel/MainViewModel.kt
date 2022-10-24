package com.ielts.preparation.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ielts.preparation.data.prefs.AppPrefs
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            delay(5000)
            _isLoading.value = false
        }
    }
    val pref = AppPrefs()
    fun getToolbarTITLE(context: Context) = pref.getToolbarTITLE(context)
    fun getButtonVisibility(context: Context) = pref.getButtonVisibility(context)
}