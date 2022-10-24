package com.ielts.preparation.ui.speaking.speakingLessons.fragment.speakingLessonHome

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ielts.preparation.R
import com.ielts.preparation.data.LessonItems
import com.ielts.preparation.databinding.FragmentSpeakingLessonHomeBinding
import com.ielts.preparation.ui.dashboard.viewModel.DashboardViewModel
import com.ielts.preparation.utils.helpers.JsonUtils
import com.ielts.preparation.viewModel.lessonViewModel.LessonViewModel
import com.ielts.preparation.viewModel.lessonViewModel.LessonViewModelFactory
import kotlinx.coroutines.launch

class SpeakingLessonHomeFragment : Fragment() {
    private lateinit var binding: FragmentSpeakingLessonHomeBinding
    private var lessonJson: String? = ""
    private lateinit var lessonViewModel: LessonViewModel
    private lateinit var lessonViewModelFactory: LessonViewModelFactory
    private var speakingItemsList = mutableListOf<LessonItems>()
    private var commonsArray = arrayOf<LessonItems>()
    private var speakingPartOneArray = arrayOf<LessonItems>()
    private var speakingPartTwoArray = arrayOf<LessonItems>()
    private var speakingPartThreeArray = arrayOf<LessonItems>()
    private lateinit var dashboardViewModel: DashboardViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSpeakingLessonHomeBinding.inflate(layoutInflater)
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


        speakingItemsList = lessonViewModel.filterSpeakingItems

        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            getFilteredListByMainTopics(speakingItemsList)
            Log.i("checkSMT", commonsArray.size.toString())

            cvSpeakingCommon.setOnClickListener {
                findNavController().navigate(SpeakingLessonHomeFragmentDirections.actionSpeakingLessonHomeFragmentToSpeakingLessonMainTopicsFragment(
                    commonsArray
                ))
            }
            cvSpeakingPart1.setOnClickListener {
                findNavController().navigate(SpeakingLessonHomeFragmentDirections.actionSpeakingLessonHomeFragmentToSpeakingLessonMainTopicsFragment(
                    speakingPartOneArray
                ))
            }
            cvSpeakingPart2.setOnClickListener {
                findNavController().navigate(SpeakingLessonHomeFragmentDirections.actionSpeakingLessonHomeFragmentToSpeakingLessonMainTopicsFragment(
                    speakingPartTwoArray
                ))
            }
            cvSpeakingPart3.setOnClickListener {
                findNavController().navigate(SpeakingLessonHomeFragmentDirections.actionSpeakingLessonHomeFragmentToSpeakingLessonMainTopicsFragment(
                    speakingPartThreeArray
                ))
            }
        }
    }

    private fun getFilteredListByMainTopics(speakingItemsList: MutableList<LessonItems>) {
        lifecycleScope.launch {
            speakingItemsList.forEach {
                if (it.mainTopic == "Common" && !it.title.isNullOrEmpty()){
                    if (!commonsArray.contains(it)){
                        commonsArray += arrayOf(
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

                if (it.mainTopic == "Speaking Part 1" && !it.title.isNullOrEmpty()){
                    if (!speakingPartOneArray.contains(it)){
                        speakingPartOneArray += arrayOf(
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
                if (it.mainTopic == "Speaking Part 2" && !it.title.isNullOrEmpty()){
                    if (!speakingPartTwoArray.contains(it)){
                        speakingPartTwoArray += arrayOf(
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
                if (it.mainTopic == "Speaking Part 3" && !it.title.isNullOrEmpty()){
                    if (!speakingPartThreeArray.contains(it)){
                        speakingPartThreeArray += arrayOf(
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
    }


    override fun onResume() {
        super.onResume()
        dashboardViewModel.saveTitle(requireContext(), getString(R.string.speaking_actionbar))
        dashboardViewModel.saveButtonVisibility(requireContext(), true)
    }
}