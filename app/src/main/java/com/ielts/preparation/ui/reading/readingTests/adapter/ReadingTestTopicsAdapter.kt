package com.ielts.preparation.ui.reading.readingTests.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ielts.preparation.databinding.ReadingTestMainTopicItemsBinding
import com.ielts.preparation.ui.reading.readingTests.data.ReadingTestsItems

class ReadingTestTopicsAdapter(
  private val topicList : List<ReadingTestsItems>,
  val itemOnClick : (String) -> Unit
) : RecyclerView.Adapter<ReadingTestTopicsAdapter.ViewHolder>() {
    class ViewHolder (
        val binding : ReadingTestMainTopicItemsBinding
            ): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        binding = ReadingTestMainTopicItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = topicList[position]
        holder.binding.apply {
            tvTestTitle.text = currentItem.title
            tvTestTopic.text = currentItem.topic
        }

        holder.itemView.setOnClickListener {
            currentItem.title?.let { title ->
                itemOnClick(
                    title
                )
            }
        }
    }

    override fun getItemCount(): Int = topicList.size
}