package com.ielts.preparation.ui.vocabulary.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VocabularyViewModelFactory(
    var vocabJson : String? = ""
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = VocabularyViewModel(vocabJson) as T
}