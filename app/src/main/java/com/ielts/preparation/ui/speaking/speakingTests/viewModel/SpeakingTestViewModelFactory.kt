package com.ielts.preparation.ui.speaking.speakingTests.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SpeakingTestViewModelFactory(
    private var speakingTestJson : String? = ""
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = SpeakingTestViewModel(speakingTestJson) as T
}