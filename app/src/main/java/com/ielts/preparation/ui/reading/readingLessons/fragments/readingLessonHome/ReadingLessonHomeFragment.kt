package com.ielts.preparation.ui.reading.readingLessons.fragments.readingLessonHome

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ielts.preparation.R
import com.ielts.preparation.data.LessonItems
import com.ielts.preparation.databinding.FragmentReadingLessonHomeBinding
import com.ielts.preparation.ui.dashboard.viewModel.DashboardViewModel
import com.ielts.preparation.utils.helpers.JsonUtils
import com.ielts.preparation.viewModel.lessonViewModel.LessonViewModel
import com.ielts.preparation.viewModel.lessonViewModel.LessonViewModelFactory
import kotlinx.coroutines.launch


class ReadingLessonHomeFragment : Fragment() {
    private lateinit var binding: FragmentReadingLessonHomeBinding
    private var lessonJson: String? = ""
    private lateinit var lessonViewModel: LessonViewModel
    private lateinit var lessonViewModelFactory: LessonViewModelFactory
    private var readingItemsList = mutableListOf<LessonItems>()
    private var questionTypesArray = arrayOf<LessonItems>()
    private var readingSkillsArray = arrayOf<LessonItems>()
    private lateinit var dashboardViewModel: DashboardViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReadingLessonHomeBinding.inflate(layoutInflater)
        lessonJson = JsonUtils.getJsonDataFromAsset(
            requireContext(),
            "ielts_test/lesson/ielts_lesson.json"
        )
        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            lessonViewModelFactory = LessonViewModelFactory(lessonJson)
            lessonViewModel = ViewModelProvider(
                this@ReadingLessonHomeFragment,
                lessonViewModelFactory
            )[LessonViewModel::class.java]

            readingItemsList = lessonViewModel.filterReadingItems

            filterReadingListByMainTopic(readingItemsList)

            binding.cvQuestionTypes.setOnClickListener {
                findNavController().navigate(
                    ReadingLessonHomeFragmentDirections.actionReadingLessonHomeFragmentToReadingQuestionTypesFragment(
                        questionTypesArray
                    )
                )
            }
            binding.cvReadingSkills.setOnClickListener {
                findNavController().navigate(
                    ReadingLessonHomeFragmentDirections.actionReadingLessonHomeFragmentToReadingSkillsFragment(
                        readingSkillsArray
                    )
                )
            }


        }
    }

    private fun filterReadingListByMainTopic(readingItemsList: MutableList<LessonItems>) {
        readingItemsList.forEach {
            if (it.mainTopic == "Question Types" && !it.title.isNullOrEmpty()) {
                if (!questionTypesArray.contains(it)) {
                    questionTypesArray += arrayOf(
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
            if (it.mainTopic == "Reading Skills" && !it.title.isNullOrEmpty()) {
                if (!readingSkillsArray.contains(it)) {
                    readingSkillsArray += arrayOf(
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
        dashboardViewModel.saveTitle(requireContext(), getString(R.string.reading_lesson_home_actionbar))
        dashboardViewModel.saveButtonVisibility(requireContext(), true)
    }
}

