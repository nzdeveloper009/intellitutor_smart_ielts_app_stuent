package com.ielts.preparation.ui.writing.writingLesson.writingLessonSubTopics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ielts.preparation.R
import com.ielts.preparation.data.LessonItems
import com.ielts.preparation.databinding.FragmentWritingLessonSubTopicsBinding
import com.ielts.preparation.ui.dashboard.viewModel.DashboardViewModel
import com.ielts.preparation.ui.writing.writingLesson.adapter.WritingLessonMainTopicsAdapter


class WritingLessonSubTopicsFragment : Fragment() {
    private lateinit var binding: FragmentWritingLessonSubTopicsBinding
    private val args: WritingLessonSubTopicsFragmentArgs by navArgs()
    private lateinit var mainTopicAdapter: WritingLessonMainTopicsAdapter
    private lateinit var dashboardViewModel: DashboardViewModel
    private var toolbarText : String = ""
    private var clickedItemTopics: Array<LessonItems> = arrayOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWritingLessonSubTopicsBinding.inflate(layoutInflater)
        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
        clickedItemTopics = args.writingCommonItems
        clickedItemTopics.forEach {
            toolbarText = it.mainTopic.toString()
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainTopicAdapter = WritingLessonMainTopicsAdapter(clickedItemTopics) { content ->
            onClick(content)
        }
        binding.rvWritingLessonCommons.apply {
            adapter = mainTopicAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun onClick(content: LessonItems) {
        findNavController().navigate(
            WritingLessonSubTopicsFragmentDirections.actionWritingLessonCommonsFragmentToWritingLessonCommonExplanationFragment(
                content
            )
        )
    }

    override fun onResume() {
        super.onResume()
        dashboardViewModel.saveTitle(requireContext(), toolbarText)
        dashboardViewModel.saveButtonVisibility(requireContext(), true)
    }
}