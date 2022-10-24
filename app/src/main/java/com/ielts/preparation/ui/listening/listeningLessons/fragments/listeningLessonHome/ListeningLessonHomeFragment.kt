package com.ielts.preparation.ui.listening.listeningLessons.fragments.listeningLessonHome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ielts.preparation.R
import com.ielts.preparation.data.LessonItems
import com.ielts.preparation.databinding.FragmentListeningLessonHomeBinding
import com.ielts.preparation.ui.dashboard.viewModel.DashboardViewModel
import com.ielts.preparation.utils.helpers.JsonUtils
import com.ielts.preparation.viewModel.lessonViewModel.LessonViewModel
import com.ielts.preparation.viewModel.lessonViewModel.LessonViewModelFactory
import kotlinx.coroutines.launch

class ListeningLessonHomeFragment : Fragment() {
    private lateinit var binding: FragmentListeningLessonHomeBinding
    private var lessonJson: String? = ""
    private lateinit var lessonViewModel: LessonViewModel
    private lateinit var lessonViewModelFactory: LessonViewModelFactory
    private var listeningItemsList: MutableList<LessonItems> = mutableListOf()
    private lateinit var dashboardViewModel: DashboardViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListeningLessonHomeBinding.inflate(layoutInflater)

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


        listeningItemsList = lessonViewModel.filterListeningItems
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            cvListeningPracticeLessons.setOnClickListener {

                findNavController().navigate(ListeningLessonHomeFragmentDirections.actionListeningLessonHomeFragmentToListeningLessonMainTopicsFragment(
                    filterListByMainTopics("Listening Practice Lessons")
                ))
            }
            cvIeltsListeningTips.setOnClickListener {
                findNavController().navigate(ListeningLessonHomeFragmentDirections.actionListeningLessonHomeFragmentToListeningLessonMainTopicsFragment(
                    filterListByMainTopics("IELTS Listening Tips")
                ))
            }
        }

    }

    private fun filterListByMainTopics(topicName: String): Array<LessonItems> {
        var filteredItemArray = arrayOf<LessonItems>()
        lifecycleScope.launch {
            listeningItemsList.forEach { it  ->
                if (it.mainTopic == topicName && !it.title.isNullOrEmpty()) {
                    if (!filteredItemArray.contains(it)) {
                        filteredItemArray += arrayOf(
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
        return filteredItemArray
    }


    override fun onResume() {
        super.onResume()
        dashboardViewModel.saveTitle(requireContext(), getString(R.string.listening_actionbar))
        dashboardViewModel.saveButtonVisibility(requireContext(), true)
    }
}