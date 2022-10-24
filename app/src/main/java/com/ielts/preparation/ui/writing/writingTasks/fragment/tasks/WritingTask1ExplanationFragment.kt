package com.ielts.preparation.ui.writing.writingTasks.fragment.tasks

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Html
import android.text.method.ScrollingMovementMethod
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
import com.ielts.preparation.databinding.FragmentWritingTask1ExplanationBinding
import com.ielts.preparation.ui.dashboard.viewModel.DashboardViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream
import java.util.*

class WritingTask1ExplanationFragment : Fragment(), TextToSpeech.OnInitListener {
    private lateinit var binding: FragmentWritingTask1ExplanationBinding
    private val args: WritingTask1ExplanationFragmentArgs by navArgs()
    private lateinit var dashboardViewModel: DashboardViewModel
    private var question: String = ""
    private var questionImageName: String = ""
    private var answer: String = ""
    private var textToSpeech: TextToSpeech? = null
    private var buttonSpeak: ImageButton? = null
    private var speakCheck = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWritingTask1ExplanationBinding.inflate(layoutInflater)
        binding.tvAnswerDetails.isVisible = false
        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]


        question = args.question
        questionImageName = "args.questionImageName"
        answer = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(args.answer, Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            Html.fromHtml(args.answer).toString()
        }
        buttonSpeak = this.binding.ibSpeakText
        buttonSpeak?.isEnabled = false
        textToSpeech = TextToSpeech(requireContext(), this)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            try {
                // get input stream
                val inputStream: InputStream =
                    resources.assets.open("ielts_test/writing/t1_image/${args.questionImageName}.jpg")
                // load image as Drawable
                val drawable = Drawable.createFromStream(inputStream, null)
                binding.apply {
                    tvQuestionDetails.movementMethod = ScrollingMovementMethod()
                    tvAnswerDetails.movementMethod = ScrollingMovementMethod()

                    tvQuestionDetails.text = question
                    imageViewQuestionImage.setImageDrawable(drawable)

                    btnDisplayAnswer.setOnClickListener {
                        if (!tvAnswerDetails.isVisible){
                            btnDisplayAnswer.rotation = 180F
                            tvAnswerDetails.isVisible = true
                            tvAnswerDetails.text = answer
                        }else{
                            btnDisplayAnswer.rotation = 0F
                            tvAnswerDetails.isVisible = false
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
            } catch (ex: IOException) {
                Toast.makeText(requireContext(), "$ex", Toast.LENGTH_LONG).show()
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
            textToSpeech?.speak(question.toString(), TextToSpeech.QUEUE_FLUSH, null, "")
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