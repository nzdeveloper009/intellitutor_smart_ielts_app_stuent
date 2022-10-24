package com.ielts.preparation.ui.writing.writingLesson.writingLessonSubTopics

import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.ielts.preparation.R
import com.ielts.preparation.data.LessonItems
import com.ielts.preparation.databinding.FragmentWritingLessonSubTopicsExplanationBinding
import com.ielts.preparation.ui.dashboard.viewModel.DashboardViewModel

class WritingLessonSubTopicsExplanationFragment : Fragment() {
    private lateinit var binding: FragmentWritingLessonSubTopicsExplanationBinding
    private val args: WritingLessonSubTopicsExplanationFragmentArgs by navArgs()
    private lateinit var dashboardViewModel: DashboardViewModel

    private var receivedItem: LessonItems? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWritingLessonSubTopicsExplanationBinding.inflate(layoutInflater)
        receivedItem = args.receivedContent
        binding.cvSource.isVisible=false
        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]


        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tvMainTopic.text = receivedItem?.mainTopic
            tvLessonTitleName.text = receivedItem?.title
            tvLessonExplanation.text = convertHtmlToString(receivedItem?.content)
        }
    }

    private fun convertHtmlToString(htmlToConvert: String?): Spanned {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(htmlToConvert, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(htmlToConvert)
        }
    }

    override fun onResume() {
        super.onResume()
        dashboardViewModel.saveTitle(requireContext(), getString(R.string.writing_actionbar))
        dashboardViewModel.saveButtonVisibility(requireContext(), true)
    }
}