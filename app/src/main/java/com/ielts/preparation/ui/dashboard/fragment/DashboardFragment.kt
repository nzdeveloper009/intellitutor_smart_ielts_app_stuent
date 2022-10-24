package com.ielts.preparation.ui.dashboard.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ielts.preparation.R
import com.ielts.preparation.databinding.CustomDialogLayoutBinding
import com.ielts.preparation.databinding.FragmentDashboardBinding
import com.ielts.preparation.ui.dashboard.adapter.DashboardAdapter
import com.ielts.preparation.ui.dashboard.repo.MainDashboardItemsRepo
import com.ielts.preparation.ui.dashboard.viewModel.DashboardViewModel


class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var mainDashboardItemsRepo: MainDashboardItemsRepo
    private lateinit var dashboardAdapter: DashboardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressed()
    }

    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher
            .addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                    val customDialogView = LayoutInflater.from(requireContext())
                        .inflate(R.layout.custom_dialog_layout, null)
                    val customDialogViewBinding = CustomDialogLayoutBinding.bind(customDialogView)
                    val alertDialogBuilder = MaterialAlertDialogBuilder(requireContext(),
                        R.style.MyRounded_MaterialComponents_MaterialAlertDialog)
                        .setView(customDialogView)
                        .setCancelable(false)

                    val alertDialog = alertDialogBuilder.show()

                    customDialogViewBinding.btnNo.setOnClickListener {
                        alertDialog.dismiss()
                    }
                    customDialogViewBinding.btnYes.setOnClickListener {
                        requireActivity().finish()
                    }
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDashboardBinding.inflate(layoutInflater)
        binding.progressBar.isVisible = false
        // Inflate the layout for this fragment
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainDashboardItemsRepo = MainDashboardItemsRepo(requireContext())
        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
        dashboardViewModel.addItemsToDashboard(mainDashboardItemsRepo.getDashboardItems())

        observeDashboardItems()

        binding.apply {
            btnVocabulary.setOnClickListener {
                displayProgressIfSlow()
                findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToVocabularyTopicsFragment())
            }
            btnTips.setOnClickListener {
                findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToIeltsTipsFragment())
            }
            btnGrammar.setOnClickListener {
                findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToGrammarHomeFragment())
            }
        }
    }

    private fun observeDashboardItems() {
        val dashBoardItemList = dashboardViewModel.dashboardItems

        dashboardAdapter = DashboardAdapter(dashBoardItemList) { adapterPosition ->
            onClick(adapterPosition)
        }
        binding.rvMainDashboard.apply {
            adapter = dashboardAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 2)
        }

    }

    private fun onClick(adapterPosition: Int) {
        when (adapterPosition) {
            0 -> {
                displayProgressIfSlow()
                findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToReadingLessonHomeFragment())
            }
            1 -> {
                displayProgressIfSlow()
                findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToReadingTestMainTopicsFragment())
            }
            2 -> {
                displayProgressIfSlow()
                findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToWritingLessonHomeFragment())
            }
            3 -> {
                displayProgressIfSlow()
                findNavController().navigate(
                    DashboardFragmentDirections.actionDashboardFragmentToWritingTasksFragment(
                        "Writing Task 1"
                    )
                )
            }
            4 -> {
                displayProgressIfSlow()
                findNavController().navigate(
                    DashboardFragmentDirections.actionDashboardFragmentToWritingTasksFragment(
                        "Writing Task 2"
                    )
                )
            }
            5 -> {
                displayProgressIfSlow()
                findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToSpeakingLessonHomeFragment())
            }
            6 -> {
                displayProgressIfSlow()
                findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToSpeakingTestHomeFragment())
            }
            7 -> {
                displayProgressIfSlow()
                findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToListeningLessonHomeFragment())
            }
            8 -> {
                displayProgressIfSlow()
                findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToListeningTestMainTopicsFragment())
            }
        }
    }

    private fun displayProgressIfSlow() {
        binding.progressBar.isVisible =
            Build.VERSION.SDK_INT == Build.VERSION_CODES.N || Build.VERSION.SDK_INT == 27 || Build.VERSION.SDK_INT == 26
    }

    override fun onResume() {
        super.onResume()
        dashboardViewModel.saveTitle(
            requireContext(),
            getString(R.string.ielts_preparation_actionbar)
        )
        dashboardViewModel.saveButtonVisibility(requireContext(), false)
    }


}