package com.ielts.preparation.ui.grammarForIELTS.fragments.tenses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.ielts.preparation.databinding.FragmentTensesExplanationBinding

class TensesExplanationFragment : Fragment() {
    private lateinit var binding: FragmentTensesExplanationBinding
    private val args: TensesExplanationFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentTensesExplanationBinding.inflate(layoutInflater)
        binding.apply {
            webviewTensesExplanation.isVisible =false
            args.tenseExplanation.let {
                webviewTensesExplanation.settings.layoutAlgorithm =
                    WebSettings.LayoutAlgorithm.SINGLE_COLUMN
                webviewTensesExplanation.loadUrl(it)
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            args.tenseName.let {
                tvTenseTypeName.text = it
            }
            webviewTensesExplanation.isVisible = true
        }
    }
}
