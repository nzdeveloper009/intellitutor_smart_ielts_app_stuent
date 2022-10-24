package com.ielts.preparation.ui.grammarForIELTS.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ielts.preparation.R
import com.ielts.preparation.databinding.PartsOfSpeechItemsBinding
import com.ielts.preparation.ui.grammarForIELTS.data.PartsOfSpeechItems

class PartsOfSpeechAdapter(
    private val context: Context,
    private val itemList: List<PartsOfSpeechItems>,
    private val itemOnClick: (String) -> Unit
) : RecyclerView.Adapter<PartsOfSpeechAdapter.ViewHolder>() {

    private var selectedPosition = -1

    class ViewHolder(
        val binding: PartsOfSpeechItemsBinding,
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        binding = PartsOfSpeechItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.binding.apply {
            tvPartOfSpeechName.text = currentItem.text
            webViewPartOfSpeechExplanation.isVisible = false
            if (selectedPosition == position) {
                holder.itemView.isSelected = true
                holder.binding.apply {
                    webViewPartOfSpeechExplanation.isVisible = true
                    btnExpand.rotation = 180F
                    when (position) {
                        0 -> {
                            webViewPartOfSpeechExplanation.loadUrl("file:///android_asset/ielts_test/PartsOfSpeech/Noun.html")
                        }
                        1 -> {
                            webViewPartOfSpeechExplanation.loadUrl("file:///android_asset/ielts_test/PartsOfSpeech/Pronoun.html")
                        }
                        2 -> {
                            webViewPartOfSpeechExplanation.loadUrl("file:///android_asset/ielts_test/PartsOfSpeech/Verb.html")
                        }
                        3 -> {
                            webViewPartOfSpeechExplanation.loadUrl("file:///android_asset/ielts_test/PartsOfSpeech/Adverb.html")
                        }
                        4 -> {
                            webViewPartOfSpeechExplanation.loadUrl("file:///android_asset/ielts_test/PartsOfSpeech/Adjectives.html")
                        }
                        5 -> {
                            webViewPartOfSpeechExplanation.loadUrl("file:///android_asset/ielts_test/PartsOfSpeech/Conjunction.html")
                        }
                        6 -> {
                            webViewPartOfSpeechExplanation.loadUrl("file:///android_asset/ielts_test/PartsOfSpeech/Interjections.html")
                        }
                        7 -> {
                            webViewPartOfSpeechExplanation.loadUrl("file:///android_asset/ielts_test/PartsOfSpeech/Prepositions.html")
                        }

                    }
                }
            } else {
                holder.itemView.isSelected = false
                btnExpand.rotation = 0F
                holder.binding.apply {
                    webViewPartOfSpeechExplanation.isVisible = false
                }
            }

            holder.itemView.setOnClickListener {
                if (btnExpand.rotation == 0F){
                    itemOnClick(currentItem.text)
                    if (selectedPosition >= 0)
                        notifyItemChanged(selectedPosition)
                    selectedPosition = holder.adapterPosition
                    notifyItemChanged(selectedPosition)
                }else if (btnExpand.rotation == 180F) {
                    btnExpand.rotation = 0F
                    webViewPartOfSpeechExplanation.isVisible = false
                    itemOnClick(context.getString(R.string.parts_of_speech))
                }
            }

        }
    }


    override fun getItemCount(): Int = itemList.size
}