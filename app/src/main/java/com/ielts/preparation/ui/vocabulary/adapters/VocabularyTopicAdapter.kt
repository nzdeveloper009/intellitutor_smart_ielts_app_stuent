package com.ielts.preparation.ui.vocabulary.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.ielts.preparation.R
import com.ielts.preparation.databinding.VocabularyTopicItemsBinding


class VocabularyTopicAdapter(
    private val context: Context,
    private val topicList: MutableList<String>,
    private val filteredWords: ArrayList<Int>,
    val itemOnClick: (String, Int) -> Unit,
) : RecyclerView.Adapter<VocabularyTopicAdapter.ViewHolder>() {
    private var selectedPosition = 0

    class ViewHolder(
        val binding: VocabularyTopicItemsBinding,
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return ViewHolder(
            binding = VocabularyTopicItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val currentTopic = topicList[position]


        holder.binding.apply {
            textViewTopicName.text = currentTopic

            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
                cvVocabularyTopics.radius = 60F
            }else if(Build.VERSION.SDK_INT == 27) {
                cvVocabularyTopics.radius = 45F
            }else {
                cvVocabularyTopics.radius = 70F
            }

        }


        if (selectedPosition == position) {

            holder.itemView.isSelected = true
            holder.binding.apply {
                cvVocabularyTopics.setCardBackgroundColor(context.getColor(R.color.blue_500))
                imageViewIconItem.setImageResource(R.drawable.ic_vocabulary_blue)
                textViewTopicName.setTextColor(context.getColor(R.color.white))

            }
        } else {

            holder.itemView.isSelected = false
            holder.binding.apply {
                cvVocabularyTopics.setCardBackgroundColor(context.getColor(R.color.white))
                when (position) {
                    0 -> {
                        imageViewIconItem.setImageResource(
                            R.drawable.ic_vocabulary_blue_background,
                        )
                    }
                    1 -> {
                        imageViewIconItem.setImageResource(
                            R.drawable.ic_vocabulary_icon_orange,
                        )
                    }
                    2 -> {
                        imageViewIconItem.setImageResource(
                            R.drawable.ic_vocabulary_icon_purple
                        )
                    }
                    3 -> {
                        imageViewIconItem.setImageResource(
                            R.drawable.ic_vocabulary_blue_background,
                        )
                    }
                    4 -> {
                        imageViewIconItem.setImageResource(
                            R.drawable.ic_vocabulary_icon_orange
                        )
                    }
                    5 -> {
                        imageViewIconItem.setImageResource(
                            R.drawable.ic_vocabulary_icon_purple
                        )
                    }
                    6 -> {
                        imageViewIconItem.setImageResource(
                            R.drawable.ic_vocabulary_blue_background,
                        )
                    }
                    7 -> {
                        imageViewIconItem.setImageResource(
                            R.drawable.ic_vocabulary_icon_orange
                        )
                    }
                    8 -> {
                        imageViewIconItem.setImageResource(
                            R.drawable.ic_vocabulary_icon_purple
                        )
                    }
                    9 -> {
                        imageViewIconItem.setImageResource(
                            R.drawable.ic_vocabulary_blue_background,
                        )
                    }
                    10 -> {
                        imageViewIconItem.setImageResource(
                            R.drawable.ic_vocabulary_icon_orange
                        )
                    }
                    11 -> {
                        imageViewIconItem.setImageResource(
                            R.drawable.ic_vocabulary_icon_purple
                        )
                    }
                    12 -> {
                        imageViewIconItem.setImageResource(
                            R.drawable.ic_vocabulary_blue_background,
                        )
                    }
                    13 -> {
                        imageViewIconItem.setImageResource(
                            R.drawable.ic_vocabulary_icon_orange
                        )
                    }
                    14 -> {
                        imageViewIconItem.setImageResource(
                            R.drawable.ic_vocabulary_icon_purple
                        )
                    }
                    15 -> {
                        imageViewIconItem.setImageResource(
                            R.drawable.ic_vocabulary_blue_background,
                        )
                    }
                    16 -> {
                        imageViewIconItem.setImageResource(
                            R.drawable.ic_vocabulary_icon_orange
                        )
                    }
                    17 -> {
                        imageViewIconItem.setImageResource(
                            R.drawable.ic_vocabulary_icon_purple
                        )
                    }
                    18 -> {
                        imageViewIconItem.setImageResource(
                            R.drawable.ic_vocabulary_blue_background,
                        )
                    }
                }
                textViewTopicName.setTextColor(context.getColor(R.color.black))
            }
        }


        holder.itemView.setOnClickListener {
            itemOnClick(
                currentTopic,
                position
            )
            if (selectedPosition >= 0)
                notifyItemChanged(selectedPosition)
            selectedPosition = holder.adapterPosition
            notifyItemChanged(selectedPosition)
        }
    }

    override fun getItemCount(): Int {
        return topicList.size
    }

}