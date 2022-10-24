package com.ielts.preparation.ui.ieltsTips.fragment.ieltsTipsExplanation

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Html
import android.text.Spanned
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
import com.ielts.preparation.databinding.FragmentIeltsTipsExplanationBinding
import com.ielts.preparation.ui.dashboard.viewModel.DashboardViewModel
import com.ielts.preparation.ui.ieltsTips.data.IeltsTipsItems
import java.util.*

class IeltsTipsExplanationFragment : Fragment(), TextToSpeech.OnInitListener {
    private lateinit var binding: FragmentIeltsTipsExplanationBinding
    private val args: IeltsTipsExplanationFragmentArgs by navArgs()
    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var itemDetails: IeltsTipsItems
    private var tipContent: String? = ""
    private var tipSource: String? = ""
    private var tipTitle: String? = ""
    private var speakCheck = 0
    private var textToSpeech: TextToSpeech? = null
    private var buttonSpeak: ImageButton? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentIeltsTipsExplanationBinding.inflate(layoutInflater)
        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]

        binding.tvSourceDescription.isVisible = false
        binding.tvTipContentExplanation.isVisible = false
        itemDetails = args.selectedItem
        tipTitle = itemDetails.tipTitle
        tipContent = itemDetails.tipContent
        tipSource = itemDetails.tipSource

        buttonSpeak = this.binding.btnSpeakText
        buttonSpeak?.isEnabled = false
        textToSpeech = TextToSpeech(requireContext(), this)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tvTitleName.text = convertHtmlToString(tipTitle)


            btnDisplayContent.rotation = 180F
            tvTipContentExplanation.isVisible = true
            tvTipContentExplanation.text = convertHtmlToString(tipContent)

            btnDisplaySource.rotation = 180F
            tvSourceDescription.isVisible = true
            tvSourceDescription.text = convertHtmlToString(tipSource)

            btnDisplayContent.setOnClickListener {
                if (!tvTipContentExplanation.isVisible) {
                    btnDisplayContent.rotation = 180F
                    tvTipContentExplanation.isVisible = true
                    tvTipContentExplanation.text = convertHtmlToString(tipContent)
                } else {
                    btnDisplayContent.rotation = 0F
                    tvTipContentExplanation.isVisible = false
                }

            }
            btnDisplaySource.setOnClickListener {
                if (!tvSourceDescription.isVisible) {
                    btnDisplaySource.rotation = 180F
                    tvSourceDescription.isVisible = true
                    tvSourceDescription.text = convertHtmlToString(tipSource)
                } else {
                    btnDisplaySource.rotation = 0F
                    tvSourceDescription.isVisible = false
                }

            }

            btnSpeakText.setOnClickListener {
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

    private fun convertHtmlToString(textToConvert: String?): Spanned? {
        val convertedString: Spanned?
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            convertedString = Html.fromHtml(textToConvert, Html.FROM_HTML_MODE_LEGACY)
        } else {
            convertedString = Html.fromHtml(textToConvert)
        }

        return convertedString
    }

    override fun onResume() {
        super.onResume()
        dashboardViewModel.saveTitle(requireContext(), getString(R.string.tips_actionBar))
        dashboardViewModel.saveButtonVisibility(requireContext(), true)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = textToSpeech?.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(requireContext(),
                    getString(R.string.lang_not_supported),
                    Toast.LENGTH_LONG).show()
            } else {
                buttonSpeak?.isEnabled = true
            }
        } else {
            Toast.makeText(requireContext(),
                getString(R.string.failed_initialization),
                Toast.LENGTH_LONG).show()
        }
    }

    private fun speakOut() {
        if (textToSpeech != null){
            textToSpeech?.speak(tipTitle.toString(), TextToSpeech.QUEUE_FLUSH, null, "")
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