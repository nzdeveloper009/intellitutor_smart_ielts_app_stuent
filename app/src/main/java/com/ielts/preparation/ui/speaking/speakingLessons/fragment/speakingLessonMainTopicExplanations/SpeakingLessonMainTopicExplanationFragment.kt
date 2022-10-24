package com.ielts.preparation.ui.speaking.speakingLessons.fragment.speakingLessonMainTopicExplanations

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.ielts.preparation.R
import com.ielts.preparation.data.LessonItems
import com.ielts.preparation.databinding.FragmentSpeakingLessonMainTopicExplanationBinding
import com.ielts.preparation.ui.dashboard.viewModel.DashboardViewModel


class SpeakingLessonMainTopicExplanationFragment : Fragment() {
    private lateinit var binding: FragmentSpeakingLessonMainTopicExplanationBinding
    private val args: SpeakingLessonMainTopicExplanationFragmentArgs by navArgs()
    private lateinit var receivedItem: LessonItems
    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSpeakingLessonMainTopicExplanationBinding.inflate(layoutInflater)
        receivedItem = args.receivedItems
        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val contentExplanation = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(receivedItem.content, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(receivedItem.content)
        }

        binding.apply {

            tvSpeakingLessonMainTopic.text = receivedItem.mainTopic
            tvSpeakingLessonSubTopic.text = receivedItem.title
            tvSpeakingContentExplanation.text = contentExplanation
            tvSpeakingSourceDescription.text = receivedItem.source

        }

    }



    override fun onResume() {
        super.onResume()
        dashboardViewModel.saveTitle(requireContext(), getString(R.string.speaking_actionbar))
        dashboardViewModel.saveButtonVisibility(requireContext(), true)
    }

}