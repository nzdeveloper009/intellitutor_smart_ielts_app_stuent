package com.ielts.preparation.ui.writing.writingTasks.fragment.tasks

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.ielts.preparation.R
import com.ielts.preparation.databinding.FragmentWritingTask2ExplanationBinding
import com.ielts.preparation.ui.dashboard.viewModel.DashboardViewModel
import java.util.*

class WritingTask2ExplanationFragment : Fragment(), TextToSpeech.OnInitListener {
    private lateinit var binding: FragmentWritingTask2ExplanationBinding
    private val args: WritingTask2ExplanationFragmentArgs by navArgs()
    private lateinit var dashboardViewModel: DashboardViewModel
    private var speakCheck = 0
    private var textToSpeech: TextToSpeech? = null
    private var buttonSpeak: ImageButton? = null
    private var questionDetails : String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentWritingTask2ExplanationBinding.inflate(layoutInflater)
        binding.tvAnswerDetailsTask2.isVisible = false
        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
        questionDetails = args.question
        buttonSpeak = this.binding.ibSpeakText
        buttonSpeak?.isEnabled = false
        textToSpeech = TextToSpeech(requireContext(), this)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvQuestionDetailsTask2.text = questionDetails


        binding.apply {
            btnDisplayAnswer.setOnClickListener {
                if (!tvAnswerDetailsTask2.isVisible) {
                    btnDisplayAnswer.rotation = 180F
                    tvAnswerDetailsTask2.isVisible = true
                    tvAnswerDetailsTask2.text =
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            Html.fromHtml(args.answer, Html.FROM_HTML_MODE_LEGACY)
                        } else {
                            Html.fromHtml(args.answer)
                        }
                } else {
                    btnDisplayAnswer.rotation = 0F
                    tvAnswerDetailsTask2.isVisible = false
                }
            }

            ibSpeakText.setOnClickListener {
                speakCheck = if (speakCheck == 0) {
                    speakOut()
                    1
                } else {
                    textToSpeech?.stop()
                    0
                }
            }
            }
        }


    override fun onResume() {
        super.onResume()
        dashboardViewModel.saveTitle(requireContext(), getString(R.string.writing_test_actionbar))
        dashboardViewModel.saveButtonVisibility(requireContext(), true)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = textToSpeech?.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(requireContext(),getString(R.string.lang_not_supported), Toast.LENGTH_LONG ).show()
            } else {
                buttonSpeak?.isEnabled = true
            }
        } else {
            Toast.makeText(requireContext(),getString(R.string.failed_initialization), Toast.LENGTH_LONG ).show()
        }    }

    private fun speakOut() {
        if (textToSpeech != null){
            textToSpeech?.speak(questionDetails.toString(), TextToSpeech.QUEUE_FLUSH, null, "")
        }else {
            Toast.makeText(requireContext(),getString(R.string.textNotFoundToast),Toast.LENGTH_LONG).show()
        }

    }

    override fun onDestroy() {
        // Shutdown TTS
        if (textToSpeech != null) {
            textToSpeech?.stop()
            textToSpeech?.shutdown()
        }
        super.onDestroy()
    }
}