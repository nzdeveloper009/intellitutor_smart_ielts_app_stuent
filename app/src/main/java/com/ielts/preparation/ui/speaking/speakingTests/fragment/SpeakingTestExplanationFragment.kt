package com.ielts.preparation.ui.speaking.speakingTests.fragment

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.ielts.preparation.R
import com.ielts.preparation.databinding.FragmentSpeakingTestExplanationBinding
import com.ielts.preparation.ui.dashboard.viewModel.DashboardViewModel
import com.ielts.preparation.ui.speaking.speakingTests.data.SpeakingTestItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class SpeakingTestExplanationFragment : Fragment(), TextToSpeech.OnInitListener {
    private lateinit var binding: FragmentSpeakingTestExplanationBinding
    private val args: SpeakingTestExplanationFragmentArgs by navArgs()
    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var currentItem: SpeakingTestItem
    private var questionDetails: String? = ""
    private lateinit var answer: Spanned
    private lateinit var vocabularies: Spanned
    private var speakCheck = 0
    private var textToSpeech: TextToSpeech? = null
    private var buttonSpeak: ImageButton? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSpeakingTestExplanationBinding.inflate(layoutInflater)
        currentItem = args.clickedItem
        binding.tvAnswerDetails.isVisible = false
        binding.tvVocabularyDetails.isVisible = false
        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]

        questionDetails = "${currentItem.question}\n\n${currentItem.should_say}"
        answer = convertHtmlToString( currentItem.answer)
        vocabularies =  convertHtmlToString(currentItem.vocab)
        buttonSpeak = this.binding.ibSpeakText
        buttonSpeak?.isEnabled = false
        textToSpeech = TextToSpeech(requireContext(), this)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {

            tvQuestionDetails.text = questionDetails

            btnDisplayAnswer.setOnClickListener {
                if (!tvAnswerDetails.isVisible) {
                    btnDisplayAnswer.rotation = 180F
                    tvAnswerDetails.isVisible = true
                    tvAnswerDetails.text = answer
                } else {
                    btnDisplayAnswer.rotation = 0F
                    tvAnswerDetails.isVisible = false
                }

            }
            btnDisplayVocabulary.setOnClickListener {
                if (!tvVocabularyDetails.isVisible) {
                    btnDisplayVocabulary.rotation = 180F
                    tvVocabularyDetails.isVisible = true
                    tvVocabularyDetails.text = vocabularies
                } else {
                    btnDisplayVocabulary.rotation = 0F
                    tvVocabularyDetails.isVisible = false
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
        }
    }

    private fun convertHtmlToString(htmlToConvert: String?): Spanned {
       return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(htmlToConvert, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(htmlToConvert)
        }
    }




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

    override fun onResume() {
        super.onResume()
        dashboardViewModel.saveTitle(requireContext(), getString(R.string.speaking_test_actionbar))
        dashboardViewModel.saveButtonVisibility(requireContext(), true)
    }


}
