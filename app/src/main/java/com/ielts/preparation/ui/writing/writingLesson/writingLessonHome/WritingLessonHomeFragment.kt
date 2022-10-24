package com.ielts.preparation.ui.writing.writingLesson.writingLessonHome

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ielts.preparation.R
import com.ielts.preparation.data.LessonItems
import com.ielts.preparation.databinding.FragmentWritingLessonHomeBinding
import com.ielts.preparation.ui.dashboard.viewModel.DashboardViewModel
import com.ielts.preparation.utils.helpers.JsonUtils
import com.ielts.preparation.viewModel.lessonViewModel.LessonViewModel
import com.ielts.preparation.viewModel.lessonViewModel.LessonViewModelFactory


class WritingLessonHomeFragment : Fragment() {
    private lateinit var binding: FragmentWritingLessonHomeBinding
    private var lessonJson: String? = ""
    private lateinit var lessonViewModel: LessonViewModel
    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var lessonViewModelFactory: LessonViewModelFactory
    private var writingItemsList = mutableListOf<LessonItems>()
    private var commonWritingArray: Array<LessonItems> = arrayOf()
    private var writingTask1Lesson: Array<LessonItems> = arrayOf()
    private var writingTask2Lesson: Array<LessonItems> = arrayOf()
    private var toolbarText : String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        toolbarText = getString(R.string.writing_actionbar)
        binding = FragmentWritingLessonHomeBinding.inflate(layoutInflater)
        lessonJson = JsonUtils.getJsonDataFromAsset(
            requireContext(),
            "ielts_test/lesson/ielts_lesson.json"
        )
        lessonViewModelFactory = LessonViewModelFactory(lessonJson)
        lessonViewModel = ViewModelProvider(
            this,
            lessonViewModelFactory
        )[LessonViewModel::class.java]
        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        writingItemsList = lessonViewModel.filterWritingItems
        getFilteredMainItems(writingItemsList)
        Log.i("checkCommon", commonWritingArray.size.toString())

        binding.cvWritingCommon.setOnClickListener {
            findNavController().navigate(
                WritingLessonHomeFragmentDirections.actionWritingLessonHomeFragmentToWritingLessonCommonsFragment(
                    commonWritingArray
                )
            )
        }
        binding.cvWritingTask1.setOnClickListener {
            findNavController().navigate(
                WritingLessonHomeFragmentDirections.actionWritingLessonHomeFragmentToWritingLessonCommonsFragment(
                    writingTask1Lesson
                )
            )
        }
        binding.cvWritingTask2.setOnClickListener {
            findNavController().navigate(
                WritingLessonHomeFragmentDirections.actionWritingLessonHomeFragmentToWritingLessonCommonsFragment(
                    writingTask2Lesson
                )
            )
        }
    }

    private fun getFilteredMainItems(writingItemsList: MutableList<LessonItems>) {
        writingItemsList.forEach {
            if (it.mainTopic == "Common" && !it.title.isNullOrEmpty()) {
                if (!commonWritingArray.contains(it)) {
                    commonWritingArray += arrayOf(
                        LessonItems(
                            it.content,
                            it.mainTopic,
                            it.remark,
                            it.section,
                            it.source,
                            it.subTopic,
                            it.title,
                            it.topic
                        )
                    )
                }
            }
            if (it.mainTopic == "Writing Task 1" && !it.title.isNullOrEmpty()) {
                if (!writingTask1Lesson.contains(it)) {
                    writingTask1Lesson += arrayOf(
                        LessonItems(
                            it.content,
                            it.mainTopic,
                            it.remark,
                            it.section,
                            it.source,
                            it.subTopic,
                            it.title,
                            it.topic
                        )
                    )
                }
            }
            if (it.mainTopic == "Writing Task 2" && !it.title.isNullOrEmpty()) {
                if (!writingTask2Lesson.contains(it)) {
                    writingTask2Lesson += arrayOf(
                        LessonItems(
                            it.content,
                            it.mainTopic,
                            it.remark,
                            it.section,
                            it.source,
                            it.subTopic,
                            it.title,
                            it.topic
                        )
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        dashboardViewModel.saveTitle(requireContext(), toolbarText)
        dashboardViewModel.saveButtonVisibility(requireContext(), true)
    }

}