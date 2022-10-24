package com.ielts.preparation.ui.reading.readingLessons.fragments.contentExplanation

import android.os.Bundle
import android.text.Html
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.ielts.preparation.R
import com.ielts.preparation.data.LessonItems
import com.ielts.preparation.databinding.FragmentReadingContentExplanationBinding
import com.ielts.preparation.ui.dashboard.viewModel.DashboardViewModel

class ReadingContentExplanationFragment : Fragment() {
    private lateinit var binding: FragmentReadingContentExplanationBinding
    private val args: ReadingContentExplanationFragmentArgs by navArgs()
    private lateinit var receivedItemDetails : LessonItems
    private lateinit var dashboardViewModel: DashboardViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReadingContentExplanationBinding.inflate(layoutInflater)
         receivedItemDetails= args.clickedItemDetails
        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            "${args.position}.  ${receivedItemDetails.mainTopic}".also { questionTitle.text = it }
            tvLessonTitleName.text = receivedItemDetails.title
            val lessonExplanation =  if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                Html.fromHtml(receivedItemDetails.content, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(receivedItemDetails.content)
            }
            tvLessonExplanation.text = lessonExplanation
            tvSourceDescription.text = receivedItemDetails.source
        }

    }

    override fun onResume() {
        super.onResume()
        dashboardViewModel.saveTitle(requireContext(), getString(R.string.reading_lesson_home_actionbar))
        dashboardViewModel.saveButtonVisibility(requireContext(), true)
    }

}