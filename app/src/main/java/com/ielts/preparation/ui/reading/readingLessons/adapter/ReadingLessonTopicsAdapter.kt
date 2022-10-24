package com.ielts.preparation.ui.reading.readingLessons.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ielts.preparation.data.LessonItems
import com.ielts.preparation.databinding.LessonMainTopicItemsBinding

class ReadingLessonTopicsAdapter(
    val topicList: Array<LessonItems>,
    val itemOnClick : (LessonItems,String) -> Unit
) : RecyclerView.Adapter<ReadingLessonTopicsAdapter.ViewHolder>() {
    class ViewHolder(
        val binding: LessonMainTopicItemsBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        binding = LessonMainTopicItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = topicList[position]
            holder.binding.apply {
                "${position+1}.".also { tvNumbering.text = it }
                tvTitle.text = currentItem.title
                tvSection.text = currentItem.section
                tvLessonType.text = currentItem.mainTopic
            }
        holder.itemView.setOnClickListener {
            itemOnClick(
               currentItem,
                "${position+1}"
            )
        }
        }


    override fun getItemCount(): Int = topicList.size
}