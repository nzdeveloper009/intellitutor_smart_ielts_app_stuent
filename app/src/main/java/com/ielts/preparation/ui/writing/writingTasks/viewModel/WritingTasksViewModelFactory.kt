package com.ielts.preparation.ui.writing.writingTasks.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WritingTasksViewModelFactory(
     var writingQuestionJson: String? = "",
     var writingTask1Json: String? = "",
     var writingTask2Json: String? = ""
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = WritingTasksViewModel(
        writingQuestionJson,
        writingTask1Json,
        writingTask2Json
    ) as T
}