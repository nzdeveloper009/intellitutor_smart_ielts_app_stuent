package com.ielts.preparation.viewModel.lessonViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ielts.preparation.data.LessonItems

class LessonViewModelFactory(
    private var lessonJson: String? = ""
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = lessonJson?.let {
        LessonViewModel(
            it
        )
    } as T

}