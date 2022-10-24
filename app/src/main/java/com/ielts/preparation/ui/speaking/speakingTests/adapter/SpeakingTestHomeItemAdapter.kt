package com.ielts.preparation.ui.speaking.speakingTests.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ielts.preparation.R
import com.ielts.preparation.databinding.ReadingTestMainTopicItemsBinding
import com.ielts.preparation.ui.speaking.speakingTests.data.SpeakingTestItem

class SpeakingTestHomeItemAdapter(
    private val context: Context,
    private val speakingTestItems: MutableList<SpeakingTestItem>,
    val itemOnCLick: (SpeakingTestItem) -> Unit,
) : RecyclerView.Adapter<SpeakingTestHomeItemAdapter.ViewHolder>() {
    inner class ViewHolder(
        val binding: ReadingTestMainTopicItemsBinding,
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        binding = ReadingTestMainTopicItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = speakingTestItems[position]
        holder.binding.apply {
            "${currentItem.question}".also {
                tvTestTitle.text =
                    it
            }
            tvTestTopic.isVisible = false
        }

        holder.itemView.setOnClickListener {
            itemOnCLick(currentItem)
        }
    }

    override fun getItemCount(): Int = speakingTestItems.size
}