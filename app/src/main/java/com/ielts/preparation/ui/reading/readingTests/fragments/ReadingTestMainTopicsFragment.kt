package com.ielts.preparation.ui.reading.readingTests.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ielts.preparation.R
import com.ielts.preparation.databinding.FragmentReadingTestMainTopicsBinding
import com.ielts.preparation.ui.dashboard.viewModel.DashboardViewModel
import com.ielts.preparation.ui.reading.readingTests.adapter.ReadingTestTopicsAdapter
import com.ielts.preparation.ui.reading.readingTests.data.ReadingTestsItems
import com.ielts.preparation.ui.reading.readingTests.viewModel.ReadingTestViewModel
import com.ielts.preparation.ui.reading.readingTests.viewModel.ReadingTestViewModelFactory
import com.ielts.preparation.utils.helpers.JsonUtils
import kotlinx.coroutines.launch


class ReadingTestMainTopicsFragment : Fragment() {
    private lateinit var binding: FragmentReadingTestMainTopicsBinding
    private var readingTestJson: String? = ""
    private lateinit var readingTestFactory: ReadingTestViewModelFactory
    private lateinit var readingTestViewModel: ReadingTestViewModel
    private lateinit var readingTestAdapter: ReadingTestTopicsAdapter
    private lateinit var dashboardViewModel: DashboardViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReadingTestMainTopicsBinding.inflate(layoutInflater)

        readingTestJson = JsonUtils.getJsonDataFromAsset(
            requireContext(),
            "ielts_test/ielts_test_reading.json"
        )
        readingTestFactory = readingTestJson?.let { ReadingTestViewModelFactory(it) }!!
        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            readingTestViewModel = ViewModelProvider(this@ReadingTestMainTopicsFragment, readingTestFactory)[ReadingTestViewModel::class.java]
            val testItems = readingTestViewModel.readingTestItem
            readingTestAdapter = ReadingTestTopicsAdapter(
                testItems
            ){ title ->
                onClick(title, testItems)
            }
            binding.rvReadingtestMainTopics.apply{
                adapter = readingTestAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun onClick(title: String, testItems: MutableList<ReadingTestsItems>) {
        var selectedItemDetails : Array<ReadingTestsItems> = arrayOf()
        testItems.forEach {
            if (it.title == title){
                if (!selectedItemDetails.contains(it)){
                    selectedItemDetails += arrayOf(
                        ReadingTestsItems(
                            it.answerDetail,
                            it.answerList,
                            it.answerLocation,
                            it.extra,
                            it.paragraph,
                            it.question,
                            it.questionType,
                            it.title,
                            it.topic,
                            it.type
                        )
                    )
                }
            }
        }

        findNavController().navigate(ReadingTestMainTopicsFragmentDirections.actionReadingTestMainTopicsFragmentToReadingTestExplanationFragment(
            selectedItemDetails
        ))


    }


    override fun onResume() {
        super.onResume()
        dashboardViewModel.saveTitle(requireContext(), getString(R.string.reading_test_actionbar))
        dashboardViewModel.saveButtonVisibility(requireContext(), true)
    }

}