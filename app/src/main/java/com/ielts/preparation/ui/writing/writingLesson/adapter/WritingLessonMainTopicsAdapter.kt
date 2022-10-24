package com.ielts.preparation.ui.writing.writingLesson.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ielts.preparation.data.LessonItems
import com.ielts.preparation.databinding.LessonMainTopicItemsBinding

class WritingLessonMainTopicsAdapter(
    private val itemList: Array<LessonItems>,
    val itemOnClick : (LessonItems) -> Unit
): RecyclerView.Adapter<WritingLessonMainTopicsAdapter.ViewHolder>(){
    class ViewHolder(
        val binding: LessonMainTopicItemsBinding
    ): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(
        binding = LessonMainTopicItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val currentItem = itemList[position]
        holder.binding.apply {
            "${position+1}.".also { tvNumbering.text = it }
            tvTitle.text = currentItem.title
            tvLessonType.text = currentItem.mainTopic
            tvSection.text = currentItem.section
        }
        holder.itemView.setOnClickListener {
            itemOnClick(currentItem)
        }
    }

    override fun getItemCount(): Int = itemList.size

}