package com.ielts.preparation.ui.reading.readingLessons.fragments.readingQuestionTypes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ielts.preparation.R
import com.ielts.preparation.data.LessonItems
import com.ielts.preparation.databinding.FragmentReadingQuestionTypesBinding
import com.ielts.preparation.ui.dashboard.viewModel.DashboardViewModel
import com.ielts.preparation.ui.reading.readingLessons.adapter.ReadingLessonTopicsAdapter


class ReadingQuestionTypesFragment : Fragment() {
    private lateinit var binding: FragmentReadingQuestionTypesBinding
    private lateinit var lessonTopicsAdapter: ReadingLessonTopicsAdapter
    private val args: ReadingQuestionTypesFragmentArgs by navArgs()
    private var receivedItem : Array<LessonItems> = arrayOf()
    private lateinit var dashboardViewModel: DashboardViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReadingQuestionTypesBinding.inflate(layoutInflater)
        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        receivedItem = args.selectedTopicItems
        lessonTopicsAdapter = ReadingLessonTopicsAdapter(receivedItem){ clickedItem, position->
            onClick(clickedItem, position)
        }
        binding.rvQuestionTypes.apply {
            adapter = lessonTopicsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun onClick(clickedItem: LessonItems, position: String) {
        findNavController().navigate(ReadingQuestionTypesFragmentDirections.actionReadingQuestionTypesFragmentToReadingContentExplanationFragment(
           clickedItem,
            position
        ))
    }

    override fun onResume() {
        super.onResume()
        dashboardViewModel.saveTitle(requireContext(), getString(R.string.reading_question_types_actionbar))
        dashboardViewModel.saveButtonVisibility(requireContext(), true)
    }
}