package com.ielts.preparation.ui.listening.listeningTests.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ielts.preparation.R
import com.ielts.preparation.databinding.FragmentListeningTestMainTopicExplanationBinding
import com.ielts.preparation.ui.dashboard.viewModel.DashboardViewModel
import com.ielts.preparation.ui.listening.listeningTests.viewModel.ListeningTestViewModel
import com.ielts.preparation.ui.listening.listeningTests.viewModel.ListeningTestsViewModelFactory
import com.ielts.preparation.ui.reading.readingTests.fragments.ReadingTestExplanationFragmentDirections
import com.ielts.preparation.utils.helpers.JsonUtils
import kotlinx.coroutines.launch
import org.jetbrains.annotations.NotNull

class ListeningTestMainTopicExplanationFragment : Fragment() {
    private lateinit var binding: FragmentListeningTestMainTopicExplanationBinding
    private val args: ListeningTestMainTopicExplanationFragmentArgs by navArgs()
    private lateinit var listeningTestViewModel: ListeningTestViewModel
    private lateinit var listeningTestsViewModelFactory: ListeningTestsViewModelFactory
    private var listeningTestJson: String? = ""
    private var checkAns = 0
    private var mediaPlayer = MediaPlayer()
    private lateinit var runnable: Runnable

    private lateinit var dashboardViewModel: DashboardViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentListeningTestMainTopicExplanationBinding.inflate(layoutInflater)
        listeningTestJson = JsonUtils.getJsonDataFromAsset(
            requireContext(),
            "ielts_test/ielts_test_listening.json"
        )
        listeningTestsViewModelFactory = ListeningTestsViewModelFactory(listeningTestJson!!)
        listeningTestViewModel = ViewModelProvider(
            this,
            listeningTestsViewModelFactory
        )[ListeningTestViewModel::class.java]
        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]

        binding.btnBackward.rotation = 180F
        binding.webViewAnswers.isVisible = false
        binding.tvAnswerHeading.isVisible = false
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listeningTestViewModel.listeningTestItems.observe(viewLifecycleOwner) { listeningTestItemList ->
            listeningTestItemList.forEach { testItem ->
                if (args.clickedItem == testItem.title) {
                    val question = convertHtmlToString(testItem.question)
                    val answerDetail = convertHtmlToString(testItem.answerDetail)
                    val answerLocation = convertHtmlToString(testItem.answerLocation)
                    binding.apply {
                        webViewTestDescription.loadData(question, "text/html", "base64")

                        btnShowAnswers.setOnClickListener {
                            if (checkAns == 0) {
                                webViewTestDetails.loadData(answerLocation, "text/html", "base64")
                                webViewAnswers.isVisible = true
                                tvAnswerHeading.isVisible = true
                                webViewAnswers.loadData(answerDetail, "text/html", "base64")
                                checkAns = 1
                            } else if (checkAns == 1) {
                                webViewTestDescription.loadData(question, "text/html", "base64")
                                webViewTestDetails.isVisible = false
                                webViewAnswers.isVisible = false
                                tvAnswerHeading.isVisible = false
                                checkAns = 0
                            }
                        }
                        when (testItem.title) {
                            "Customer Order" -> {
                                mediaPlayer =
                                    MediaPlayer.create(context, R.raw.customer_order)
                            }
                            "Dolphin Conservation Trust" -> {
                                mediaPlayer = MediaPlayer.create(
                                    context,
                                    R.raw.dolphin_conservation_trust
                                )
                            }
                            "Winning in a lottery ticket" -> {
                                mediaPlayer = MediaPlayer.create(
                                    context,
                                    R.raw.winning_in_a_lottery_ticket
                                )
                            }
                            "New city developments" -> {
                                mediaPlayer =
                                    MediaPlayer.create(
                                        context,
                                        R.raw.new_city_developments
                                    )
                            }
                            "News Headlines" -> {
                                mediaPlayer =
                                    MediaPlayer.create(context, R.raw.news_headlines)
                            }
                            "Talk to new kitchen assistants" -> {
                                mediaPlayer = MediaPlayer.create(
                                    context,
                                    R.raw.talk_to_new_kitchen_assistants
                                )
                            }
                            "Fitness Holidays" -> {
                                mediaPlayer =
                                    MediaPlayer.create(context, R.raw.fitness_holidays)
                            }
                            "Expertise in creative writing" -> {
                                mediaPlayer = MediaPlayer.create(
                                    context,
                                    R.raw.expertise_in_creative_writing
                                )
                            }
                            "Paper on Public Libraries" -> {
                                mediaPlayer = MediaPlayer.create(
                                    context,
                                    R.raw.paper_on_public_libraries
                                )
                            }
                            "Telephone numbers Part 3" -> {
                                mediaPlayer = MediaPlayer.create(
                                    context,
                                    R.raw.telephone_numbers_part_3
                                )
                            }
                        }

                        btnPlayAudio.setOnClickListener {
                            seekBar.isVisible = true
                            seekBar.max = mediaPlayer.duration

                            playAudio()
                            seekBarSetup()


                        }

                        binding.btnForward.setOnClickListener {
                                val forwardTenSeconds = mediaPlayer.currentPosition + 10000
                                mediaPlayer.seekTo(forwardTenSeconds)
                            }
                        binding.btnBackward.setOnClickListener {
                            val forwardTenSeconds = mediaPlayer.currentPosition - 10000
                            mediaPlayer.seekTo(forwardTenSeconds)
                        }
                        seekBar.setOnSeekBarChangeListener(object :
                            SeekBar.OnSeekBarChangeListener {
                            override fun onProgressChanged(
                                seekBar: SeekBar?,
                                progress: Int,
                                fromUser: Boolean,
                            ) {
                                if (fromUser) {
                                    mediaPlayer.seekTo(progress)
                                }
                            }

                            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                            }

                            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                            }
                        })

                        ivTranslate.setOnClickListener {
                            findNavController().navigate(ListeningTestMainTopicExplanationFragmentDirections.actionListeningTestMainTopicExplanationFragmentToTranslateBottomSheetFragment(
                                "https://translate.google.com/",
                                getString(R.string.enter_a_word_and_select_a_language_to_translate_it_into)
                            ))
                        }
                    }
                }
            }
        }
    }

    private fun playAudio() {
        binding.apply {
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
                btnPlayAudio.background =
                    getDrawable(requireContext(), R.drawable.ic_pausebtn)
            } else if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                btnPlayAudio.background = getDrawable(
                    requireContext(),
                    R.drawable.ic_playbtn
                )
            }
            mediaPlayer.setOnCompletionListener {
                btnPlayAudio.background = getDrawable(
                    requireContext(),
                    R.drawable.ic_playbtn
                )
            }
        }
    }

    private fun seekBarSetup() {
        lifecycleScope.launch {
            runnable = Runnable {
                binding.seekBar.progress = mediaPlayer.currentPosition
                Handler(Looper.getMainLooper()).postDelayed(runnable, 200)
            }
            Handler(Looper.getMainLooper()).postDelayed(runnable, 0)
        }
    }

    private fun convertHtmlToString(htmlToConvert: String? = ""): String {
        return Base64.encodeToString(
            htmlToConvert?.toByteArray(),
            Base64.NO_PADDING
        )
    }


    override fun onResume() {
        super.onResume()
        dashboardViewModel.saveTitle(requireContext(), getString(R.string.listening_test_actionbar))
        dashboardViewModel.saveButtonVisibility(requireContext(), true)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
    }

}