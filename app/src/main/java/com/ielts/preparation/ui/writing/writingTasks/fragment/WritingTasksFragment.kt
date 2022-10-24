package com.ielts.preparation.ui.writing.writingTasks.fragment

import android.os.Bundle
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ielts.preparation.R
import com.ielts.preparation.databinding.FragmentWritingTasksBinding
import com.ielts.preparation.ui.dashboard.viewModel.DashboardViewModel
import com.ielts.preparation.ui.writing.writingTasks.adapter.WritingTasks1Adapter
import com.ielts.preparation.ui.writing.writingTasks.adapter.WritingTasks2Adapter
import com.ielts.preparation.ui.writing.writingTasks.viewModel.WritingTasksViewModel
import com.ielts.preparation.ui.writing.writingTasks.viewModel.WritingTasksViewModelFactory
import com.ielts.preparation.utils.helpers.JsonUtils
import kotlinx.coroutines.launch

class WritingTasksFragment : Fragment() {
    private lateinit var binding: FragmentWritingTasksBinding
    private val args: WritingTasksFragmentArgs by navArgs()
    private lateinit var writingTasksViewModelFactory: WritingTasksViewModelFactory
    private lateinit var writingTasksViewModel: WritingTasksViewModel
    private lateinit var task1Adapter: WritingTasks1Adapter
    private lateinit var task2Adapter: WritingTasks2Adapter
    private lateinit var dashboardViewModel: DashboardViewModel
    private var writingQuestionJson: String? = ""
    private var writingTask1Json: String? = ""
    private var writingTask2Json: String? = ""
    private var taskType: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWritingTasksBinding.inflate(layoutInflater)
        taskType = args.taskType
        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]

        lifecycleScope.launch {
            writingQuestionJson = JsonUtils.getJsonDataFromAsset(
                requireContext(),
                "ielts_test/writing/question.json"
            )
            writingTask1Json = JsonUtils.getJsonDataFromAsset(
                requireContext(),
                "ielts_test/writing/writing_t1.json"
            )
            writingTask2Json = JsonUtils.getJsonDataFromAsset(
                requireContext(),
                "ielts_test/writing/writing_t2.json"
            )


            writingTasksViewModelFactory = WritingTasksViewModelFactory(
                writingQuestionJson,
                writingTask1Json,
                writingTask2Json
            )


            writingTasksViewModel = ViewModelProvider(
                this@WritingTasksFragment,
                writingTasksViewModelFactory
            )[WritingTasksViewModel::class.java]
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkWhichTaskIsClicked()

    }

    private fun checkWhichTaskIsClicked() {
        when (taskType) {
            "Writing Task 1" -> {
                task1Adapter = WritingTasks1Adapter(
                    writingTasksViewModel.task1Question,
                    writingTasksViewModel.task1Answers,
                    "Writing Task 1"
                ) { question, questionImageName, answer ->
                    onTask1Click(
                        question,
                        questionImageName,
                        answer
                    )
                }
                binding.rvTask1Topics.apply {
                    adapter = task1Adapter
                    layoutManager = LinearLayoutManager(requireContext())
                }

            }
            "Writing Task 2" -> {
                task2Adapter = WritingTasks2Adapter(
                    writingTasksViewModel.task2Question,
                    writingTasksViewModel.task2Answers,
                    "Writing Essays"
                ){question, answer ->
                    onTask2Click(question,answer)
                }
                binding.rvTask1Topics.apply {
                    adapter = task2Adapter
                    layoutManager = LinearLayoutManager(requireContext())
                }
            }
        }
    }

    private fun onTask2Click(question: String, answer: String) {
        findNavController().navigate(WritingTasksFragmentDirections.actionWritingTasksFragmentToWritingTask2ExplanationFragment(
            question,
            answer
        ))
    }

    private fun onTask1Click(question: Spanned, questionImageName: String, answer: String) {
          findNavController().navigate(WritingTasksFragmentDirections.actionWritingTasksFragmentToWritingTask1ExplanationFragment(
              question.toString(),
              questionImageName,
              answer
          ))
    }

    override fun onResume() {
        super.onResume()
        dashboardViewModel.saveTitle(requireContext(), getString(R.string.writing_test_actionbar))
        dashboardViewModel.saveButtonVisibility(requireContext(), true)
    }
}