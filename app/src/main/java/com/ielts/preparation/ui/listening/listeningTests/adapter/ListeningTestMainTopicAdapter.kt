package com.ielts.preparation.ui.listening.listeningTests.adapter

import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ielts.preparation.databinding.TestsMainTopicItemsBinding
import com.ielts.preparation.ui.listening.listeningTests.data.ListeningTestItem

class ListeningTestMainTopicAdapter(
    private val itemList: MutableList<ListeningTestItem>,
    val itemOnClick : (String) -> Unit
) : RecyclerView.Adapter<ListeningTestMainTopicAdapter.ViewHolder>() {
    inner class ViewHolder(
        val binding: TestsMainTopicItemsBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        binding = TestsMainTopicItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.binding.apply {
            "${position+1}.".also { tvItemNo.text = it }
            tvTestCategory.text = currentItem.topic
            tvTestType.text = currentItem.type
            tvTitleTest.text = currentItem.title
        }

        holder.itemView.setOnClickListener {
            itemOnClick(currentItem.title)
        }
    }

    override fun getItemCount(): Int = itemList.size


}