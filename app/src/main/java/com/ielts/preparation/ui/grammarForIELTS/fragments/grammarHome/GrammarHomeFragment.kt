package com.ielts.preparation.ui.grammarForIELTS.fragments.grammarHome

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ielts.preparation.BuildConfig
import com.ielts.preparation.R
import com.ielts.preparation.databinding.GrammarHomeFragmentBinding
import com.ielts.preparation.ui.dashboard.viewModel.DashboardViewModel
import com.ielts.preparation.ui.grammarForIELTS.adapter.GrammarHomeAdapter
import com.ielts.preparation.ui.grammarForIELTS.adapter.PartsOfSpeechAdapter
import com.ielts.preparation.ui.grammarForIELTS.adapter.TensesAdapter
import com.ielts.preparation.ui.grammarForIELTS.data.PartsOfSpeechItems
import com.ielts.preparation.ui.grammarForIELTS.repo.GrammarHomeRepo
import com.ielts.preparation.ui.grammarForIELTS.repo.PartsOfSpeechRepo
import com.ielts.preparation.ui.grammarForIELTS.viewModel.GrammarViewModel


class GrammarHomeFragment() : Fragment(), TensesAdapter.ClickFirstAdapter {
    private lateinit var tensesAdapter: TensesAdapter
    private lateinit var binding: GrammarHomeFragmentBinding
    private lateinit var grammarViewModel: GrammarViewModel
    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var partsOfSpeechRepo: PartsOfSpeechRepo
    var tensesChildCategory: ArrayList<PartsOfSpeechItems> = arrayListOf()
    private var checkClickedItem = 0
    private var selectedPosition = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = GrammarHomeFragmentBinding.inflate(layoutInflater)
        grammarViewModel = ViewModelProvider(this)[GrammarViewModel::class.java]
        partsOfSpeechRepo = PartsOfSpeechRepo(requireContext())
        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
        onClick(checkClickedItem)



        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val grammarRepo = GrammarHomeRepo(requireContext())
        val grammarHomeItems = grammarRepo.getGrammarHomeItems()

        grammarViewModel.addGrammarHomeItems(grammarHomeItems)
        observeHomeItems()


    }

    private fun observeHomeItems() {
        grammarViewModel.grammarItems.observe(viewLifecycleOwner) {
            if (checkClickedItem == 0) {
                val grammarHomeAdapter =
                    GrammarHomeAdapter(requireContext(), it, 0) { adapterPosition ->
                        onClick(adapterPosition)
                    }
                binding.rvGrammarHome.apply {
                    adapter = grammarHomeAdapter
                    layoutManager =
                        GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false)
                }
            } else if (checkClickedItem == 1) {
                val grammarHomeAdapter =
                    GrammarHomeAdapter(requireContext(), it, 1) { adapterPosition ->
                        onClick(adapterPosition)
                    }
                binding.rvGrammarHome.apply {
                    adapter = grammarHomeAdapter
                    layoutManager =
                        GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false)
                }
            }
        }
    }

    private fun onClick(clickedItemPosition: Int) {

        when (clickedItemPosition) {
            0 -> {
                //parts of speech data
                binding.tvClickedPartOfSpeechName.text = getString(R.string.parts_of_speech)
                binding.tvClickedPartOfSpeechName.text = getString(R.string.parts_of_speech)
                grammarViewModel.addPartsOfSpeechItems(partsOfSpeechRepo.getPartsOfSpeech())

                grammarViewModel.partsOfSpeechItems.observe(viewLifecycleOwner) {
                    val partsOfSpeechAdapter = PartsOfSpeechAdapter(requireContext(),it) { clickedItemText ->
                        binding.tvClickedPartOfSpeechName.text = clickedItemText
                    }
                    binding.rvPartsOfSpeechItems.apply {
                        adapter = partsOfSpeechAdapter
                        layoutManager = LinearLayoutManager(requireContext())
                    }
                }
                checkClickedItem = 0
            }
            1 -> {
                //tenses data
                binding.tvClickedPartOfSpeechName.text = getString(R.string.tense)
                grammarViewModel.addTensesTypesItems(partsOfSpeechRepo.getTensesTypes())
                grammarViewModel.tensesTypeItems.forEach{
                    tensesChildCategory = it
                }

                grammarViewModel.addTensesItems(partsOfSpeechRepo.getTenses())
                grammarViewModel.tensesItems.forEach{
                    tensesAdapter = TensesAdapter(
                        requireContext(),
                        it,
                        tensesChildCategory, { clickedItemText, clickedPosition ->
                            selectedPosition = clickedPosition
                            binding.tvClickedPartOfSpeechName.text = clickedItemText
                        }, this, selectedPosition)
                    binding.rvPartsOfSpeechItems.apply {
                        adapter = tensesAdapter
                        layoutManager = LinearLayoutManager(requireContext())
                    }
                }
                checkClickedItem = 1
            }
        }
    }

    override fun onClickFirstAdapter(clickedItemText: String) {

        when (clickedItemText) {
            "Present Simple Tense" -> {
                gotoExplanationOfTenses(
                    BuildConfig.pathPresentSimpleTense,
                    "Present Simple Tense"
                )
            }
            "Present Continuous Tense" -> {
                gotoExplanationOfTenses(
                    BuildConfig.pathPresentContinuousTense,
                    "Present Continuous Tense")
            }
            "Present Perfect Tense" -> {
                gotoExplanationOfTenses(
                    BuildConfig.pathPresentPerfectTense,
                    "Present Perfect Tense")
            }
            "Present Perfect Continuous Tense" -> {
                gotoExplanationOfTenses(
                    BuildConfig.pathPresentPerfectContinuousTense,
                    "Present Perfect Continuous Tense")
            }
            "Past Simple Tense" -> {
                gotoExplanationOfTenses(
                    BuildConfig.pathPastSimpleTense,
                    "Past Simple Tense")
            }
            "Past Continuous Tense" -> {
                gotoExplanationOfTenses(
                    BuildConfig.pathPastContinuousTense,
                    "Past Continuous Tense")
            }
            "Past Perfect Tense" -> {
                gotoExplanationOfTenses(BuildConfig.pathPastPerfectTense,
                    "Past Perfect Tense")
            }
            "Past Perfect Continuous Tense" -> {
                gotoExplanationOfTenses(BuildConfig.pathPastPerfectContinuousTense,
                    "Past Perfect Continuous Tense")
            }
            "Future Simple Tense" -> {
                gotoExplanationOfTenses(
                    BuildConfig.pathFutureSimpleTense,
                    "Future Simple Tense")
            }
            "Future Continuous Tense" -> {
                gotoExplanationOfTenses(BuildConfig.pathFutureContinuousTense,
                    "Future Continuous Tense")
            }
            "Future Perfect Tense" -> {
                gotoExplanationOfTenses(BuildConfig.pathFuturePerfectTense,
                    "Future Perfect Tense")
            }
            "Future Perfect Continuous Tense" -> {
                gotoExplanationOfTenses(BuildConfig.pathFuturePerfectContinuousTense,
                    "Future Perfect Continuous Tense")
            }

        }
    }

    private fun gotoExplanationOfTenses(typeOfTense: String, nameOfTenseType: String) {
        findNavController().navigate(GrammarHomeFragmentDirections.actionGrammarHomeFragmentToTensesExplanationFragment(
            typeOfTense, nameOfTenseType))
    }

    override fun onPause() {
        super.onPause()

    }

    override fun onResume() {
        super.onResume()
        dashboardViewModel.saveTitle(requireContext(), getString(R.string.grammar_actionbar))
        dashboardViewModel.saveButtonVisibility(requireContext(), true)
        checkClickedItem = 1

        grammarViewModel.tensesItems.forEach{
            tensesAdapter = TensesAdapter(
                requireContext(),
                it,
                tensesChildCategory, { clickedItemText, clickedPosition ->
                    selectedPosition = clickedPosition
                    binding.tvClickedPartOfSpeechName.text = clickedItemText
                }, this, selectedPosition)
        }

    }

}
