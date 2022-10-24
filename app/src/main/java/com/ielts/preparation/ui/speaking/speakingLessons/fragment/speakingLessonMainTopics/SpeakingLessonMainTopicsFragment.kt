package com.ielts.preparation.ui.speaking.speakingLessons.fragment.speakingLessonMainTopics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ielts.preparation.data.LessonItems
import com.ielts.preparation.databinding.FragmentSpeakingLessonMainTopicsBinding
import com.ielts.preparation.ui.dashboard.viewModel.DashboardViewModel
import com.ielts.preparation.ui.speaking.speakingLessons.adapter.SpeakingLessonAdapter


class SpeakingLessonMainTopicsFragment : Fragment() {
    private lateinit var binding: FragmentSpeakingLessonMainTopicsBinding
    private val args: SpeakingLessonMainTopicsFragmentArgs by navArgs()
    private var receivedItems = arrayOf<LessonItems>()
    private lateinit var dashboardViewModel: DashboardViewModel
    private var toolbarText : String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSpeakingLessonMainTopicsBinding.inflate(layoutInflater)
        receivedItems = args.speakingMainTopics
        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
        receivedItems.forEach {
            toolbarText = it.mainTopic.toString()
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val speakingLessonAdapter = SpeakingLessonAdapter(receivedItems) { currentLessonItem ->
            onClick(currentLessonItem)
        }
        binding.rvSpeakingLessonMainTopics.apply {
            adapter = speakingLessonAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun onClick(currentLessonItem: LessonItems) {
        findNavController().navigate(
            SpeakingLessonMainTopicsFragmentDirections.actionSpeakingLessonMainTopicsFragmentToSpeakingLessonMainTopicExplanationFragment(
                currentLessonItem
            )
        )
    }

    override fun onResume() {
        super.onResume()
        dashboardViewModel.saveTitle(requireContext(), toolbarText)
        dashboardViewModel.saveButtonVisibility(requireContext(), true)
    }

}