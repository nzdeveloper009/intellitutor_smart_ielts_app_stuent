package com.ielts.preparation.ui.vocabulary.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.text.bold
import androidx.core.text.color
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ielts.preparation.R
import com.ielts.preparation.databinding.FragmentVocabularyTopicsBinding
import com.ielts.preparation.ui.dashboard.viewModel.DashboardViewModel
import com.ielts.preparation.ui.vocabulary.adapters.VocabularyTopicAdapter
import com.ielts.preparation.ui.vocabulary.adapters.VocabularyWordAdapter
import com.ielts.preparation.ui.vocabulary.data.VocabularyItems
import com.ielts.preparation.ui.vocabulary.viewModels.VocabularyViewModel
import com.ielts.preparation.ui.vocabulary.viewModels.VocabularyViewModelFactory
import com.ielts.preparation.utils.RequestPermissionClass
import com.ielts.preparation.utils.helpers.JsonUtils
import kotlinx.coroutines.launch


class VocabularyTopicsFragment : Fragment() {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            checkIfUserRequestedDontAskAgain()
        }
    }


    private lateinit var binding: FragmentVocabularyTopicsBinding
    private var vocabularyJson: String? = ""
    private lateinit var vocabularyViewModel: VocabularyViewModel
    private lateinit var vocabularyViewModelFactory: VocabularyViewModelFactory
    private lateinit var vocabularyTopicAdapter: VocabularyTopicAdapter
    private lateinit var vocabularyWordsAdapter: VocabularyWordAdapter
    private lateinit var dashboardViewModel: DashboardViewModel
    private val requestPermissionObj = RequestPermissionClass()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentVocabularyTopicsBinding.inflate(layoutInflater)
        binding.btnRequestPermission.isVisible = false
        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
        vocabularyJson = JsonUtils.getJsonDataFromAsset(
            requireContext(),
            "ielts_test/lesson/ielts_vocab.json"
        )
        vocabularyViewModelFactory = VocabularyViewModelFactory(vocabularyJson)
        vocabularyViewModel = ViewModelProvider(
            this@VocabularyTopicsFragment,
            vocabularyViewModelFactory
        )[VocabularyViewModel::class.java]
        onTopicClick("Growing up", 0)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRequestPermission.setOnClickListener {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", activity?.packageName, null)
            intent.data = uri
            startActivity(intent)
        }

        lifecycleScope.launch {
            binding.rvVocabularyTopics.apply {
                vocabularyTopicAdapter = VocabularyTopicAdapter(
                    requireContext(),
                    vocabularyViewModel.topicList,
                    vocabularyViewModel.wordsCounted
                ) { topicName, topicPosition ->
                    onTopicClick(topicName, topicPosition)
                }

                adapter = vocabularyTopicAdapter
                layoutManager =
                    GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false)
            }
        }
    }


    private fun onTopicClick(topicName: String, topicPosition: Int) {
        val wordsFilteredList = ArrayList<VocabularyItems>()
        val vocabularyList = vocabularyViewModel.vocabularyItems
        vocabularyList.forEach {
            if (it.topic == topicName) {
                wordsFilteredList.add(it)
            }
        }
        observeWords(wordsFilteredList)
        "Topic ${topicPosition + 1}".also {
            binding.tvTopicNumber.text = it
        }


        "${wordsFilteredList.size} words".also { binding.tvTotalWords.text = it }
    }

    private fun observeWords(wordsFilteredList: ArrayList<VocabularyItems>) {
        wordsFilteredList.sortBy { it.word }
        vocabularyWordsAdapter = VocabularyWordAdapter(requireContext(), wordsFilteredList,{
            onWordClick(it)
        }, {wordToTranslate->
            val url = "https://translate.google.com/?sl=auto&tl=en&text=${wordToTranslate}"

            btnTranslateClick(url)

        })
        val myLayoutManager =
            object : GridLayoutManager(requireContext(), 1, HORIZONTAL, false) {
                override fun canScrollHorizontally(): Boolean {
                    return false
                }
            }
        binding.rvWordsCards.apply {

            adapter = vocabularyWordsAdapter
            layoutManager = myLayoutManager
        }


        binding.btnNextWord.setOnClickListener {
            if (myLayoutManager.findLastCompletelyVisibleItemPosition() < vocabularyWordsAdapter.itemCount - 1) {
                myLayoutManager.scrollToPosition(myLayoutManager.findLastCompletelyVisibleItemPosition() + 1)
            }
        }

        binding.btnPreviousWord.setOnClickListener {
            if (myLayoutManager.findFirstCompletelyVisibleItemPosition() < vocabularyWordsAdapter.itemCount + 1) {
                myLayoutManager.scrollToPosition(myLayoutManager.findLastCompletelyVisibleItemPosition() - 1)
            }
        }
    }

    private fun btnTranslateClick(url: String) {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.INTERNET
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            findNavController().navigate(VocabularyTopicsFragmentDirections.actionVocabularyTopicsFragmentToTranslateBottomSheetFragment(
                url,
              getString(R.string.bottom_sheet_heading_vocabulary_word)
            ))
        } else {
            requestPermissionObj.requestPermission(
                requestPermissionLauncher,
                requireActivity(),
                requireContext()
            )
        }    }

    private fun onWordClick(vocabulary: VocabularyItems) {
        val dialogBuilder = MaterialAlertDialogBuilder(requireContext())
        val message = SpannableStringBuilder()
            .color(Color.BLACK) { bold { append(getString(R.string.dialog_meaning)) } }
            .color(Color.DKGRAY) { append("${vocabulary.meaning}\n\n") }
            .color(Color.BLACK) { bold { append(getString(R.string.dialog_example)) } }
            .color(Color.DKGRAY) { append("${vocabulary.example}") }

        dialogBuilder.setTitle("${vocabulary.word?.uppercase()} (${vocabulary.wordType})")
            .setMessage(message)
            .setIcon(R.drawable.ic_baseline_info_24)
            .setPositiveButton(getString(R.string.dialog_gotItButton)) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
        dialogBuilder.create()
        dialogBuilder.show()
    }

    override fun onResume() {
        super.onResume()
        dashboardViewModel.saveTitle(requireContext(),
            getString(R.string.ielts_vocabulary_actionBar_text))
        dashboardViewModel.saveButtonVisibility(requireContext(), true)
    }

    private fun checkIfUserRequestedDontAskAgain() {
        val rationalFlagRead =
            shouldShowRequestPermissionRationale(Manifest.permission.INTERNET)
        if (!rationalFlagRead) {
            binding.btnRequestPermission.isVisible = true
        }

    }
}