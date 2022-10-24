package com.ielts.preparation.ui.reading.readingTests.fragments

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ielts.preparation.R
import com.ielts.preparation.databinding.FragmentReadingTestExplanationBinding
import com.ielts.preparation.ui.dashboard.viewModel.DashboardViewModel
import com.ielts.preparation.ui.reading.readingTests.data.ReadingTestsItems
import kotlinx.coroutines.launch


class ReadingTestExplanationFragment : Fragment() {

    private var ansCheck = 0
    private lateinit var binding: FragmentReadingTestExplanationBinding
    private val args: ReadingTestExplanationFragmentArgs by navArgs()
    private var testDetails: Array<ReadingTestsItems> = arrayOf()
    private lateinit var dashboardViewModel: DashboardViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReadingTestExplanationBinding.inflate(layoutInflater)
        testDetails = args.clickedTestDetails
        binding.webViewAnswers.isVisible = false
        binding.tvAnswerHeading.isVisible = false
        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            testDetails.forEach { readingTestDetails ->
                val paragraphDetailsString = Base64.encodeToString(
                    readingTestDetails.paragraph?.toByteArray(),
                    Base64.NO_PADDING
                )
                binding.webViewTestDetails.loadData(paragraphDetailsString, "text/html", "base64")

                val questionString = Base64.encodeToString(
                    readingTestDetails.question?.toByteArray(),
                    Base64.NO_PADDING
                )
                binding.webViewTestDescription.loadData(questionString, "text/html", "base64")

            }
        }

        binding.ivAnswer.setOnClickListener {
            if (ansCheck==0) {
                lifecycleScope.launch {
                    testDetails.forEach { readingTestDetails ->
                        val answerLocationString = Base64.encodeToString(
                            readingTestDetails.answerLocation?.toByteArray(),
                            Base64.NO_PADDING
                        )
                        binding.webViewTestDetails.loadData(answerLocationString, "text/html", "base64")

                        val answerDetailsString = Base64.encodeToString(
                            readingTestDetails.answerDetail?.toByteArray(),
                            Base64.NO_PADDING
                        )
                        binding.webViewAnswers.isVisible = true
                        binding.tvAnswerHeading.isVisible = true
                        binding.webViewAnswers.loadData(answerDetailsString, "text/html", "base64")
                    }
                }
                ansCheck = 1
            } else {
                lifecycleScope.launch {
                    testDetails.forEach { readingTestDetails ->
                        val paragraphString1 = Base64.encodeToString(
                            readingTestDetails.paragraph?.toByteArray(),
                            Base64.NO_PADDING
                        )
                        binding.webViewTestDetails.loadData(paragraphString1, "text/html", "base64")

                        val questionString = Base64.encodeToString(
                            readingTestDetails.question?.toByteArray(),
                            Base64.NO_PADDING
                        )
                        binding.webViewAnswers.isVisible = false
                        binding.tvAnswerHeading.isVisible = false
                    }
                }
                ansCheck = 0
            }
        }

        binding.ivTranslate.setOnClickListener{
            findNavController().navigate(ReadingTestExplanationFragmentDirections.actionReadingTestExplanationFragmentToTranslateBottomSheetFragment(
                "https://translate.google.com/",
                getString(R.string.enter_a_word_and_select_a_language_to_translate_it_into)
            ))
        }

    }



    override fun onResume() {
        super.onResume()
        dashboardViewModel.saveTitle(requireContext(), getString(R.string.reading_test_actionbar))
        dashboardViewModel.saveButtonVisibility(requireContext(), true)
    }


}