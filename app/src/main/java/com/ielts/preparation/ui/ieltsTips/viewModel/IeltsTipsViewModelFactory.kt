package com.ielts.preparation.ui.ieltsTips.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class IeltsTipsViewModelFactory(
    var tipsJson: String? = ""
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = IeltsTipsViewModel(tipsJson) as T
}