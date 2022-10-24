package com.ielts.preparation.ui.listening.listeningTests.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ListeningTestsViewModelFactory(
    var listeningTestJson: String = ""
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        ListeningTestViewModel(listeningTestJson) as T
}
