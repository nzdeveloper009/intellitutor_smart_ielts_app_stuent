package com.ielts.preparation.ui.speaking.speakingLessons.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.ielts.preparation.data.LessonItems
import com.ielts.preparation.databinding.LessonMainTopicItemsBinding

class SpeakingLessonAdapter(
    private val itemList: Array<LessonItems> = arrayOf(),
    val itemOnClick : (LessonItems) -> Unit
) : RecyclerView.Adapter<SpeakingLessonAdapter.ViewHolder>(){
    class ViewHolder(
        val binding: LessonMainTopicItemsBinding
    ):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        binding = LessonMainTopicItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.binding.apply {
            "${position+1}.".also { tvNumbering.text = it }
            tvTitle.text = currentItem.title
            tvSection.text = currentItem.section
            tvLessonType.text = currentItem.mainTopic
        }

        holder.itemView.setOnClickListener {
            itemOnClick(
                currentItem
            )
        }
    }

    override fun getItemCount(): Int = itemList.size
}