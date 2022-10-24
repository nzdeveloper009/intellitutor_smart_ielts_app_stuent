package com.ielts.preparation.ui.grammarForIELTS.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ielts.preparation.R
import com.ielts.preparation.databinding.GrammarHomeItemsBinding
import com.ielts.preparation.ui.dashboard.data.DashboardItems

class GrammarHomeAdapter(
    private val context: Context,
    private val itemList: List<DashboardItems>,
    private var selectedPosition: Int = 0,
    val itemOnClick: (Int) -> Unit,
) : RecyclerView.Adapter<GrammarHomeAdapter.ViewHolder>() {


    class ViewHolder(
        val binding: GrammarHomeItemsBinding,
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder = ViewHolder(
        binding = GrammarHomeItemsBinding.inflate(
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
            tvTopicName.text = currentItem.itemText
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N || Build.VERSION.SDK_INT == Build.VERSION_CODES.O) {
                cvGrammarMainTopic.radius = 64F
            } else if (Build.VERSION.SDK_INT == 27) {
                cvGrammarMainTopic.radius = 45F
            } else {
                cvGrammarMainTopic.radius = 70F
            }
        }


        if (selectedPosition == position) {
            holder.itemView.isSelected = true
            holder.binding.apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tvTopicName.setTextColor(context.getColor(R.color.white))
                    cvGrammarMainTopic.setCardBackgroundColor(context.getColor(R.color.blue_500))
                } else {
                    tvTopicName.setTextColor(context.resources.getColor(R.color.white))
                    cvGrammarMainTopic.setCardBackgroundColor(context.resources.getColor(R.color.white))
                }

            }
        } else {
            holder.itemView.isSelected = false
            holder.binding.apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tvTopicName.setTextColor(context.getColor(R.color.black))
                    cvGrammarMainTopic.setCardBackgroundColor(context.getColor(R.color.white))
                } else {
                    tvTopicName.setTextColor(context.resources.getColor(R.color.white))
                    cvGrammarMainTopic.setCardBackgroundColor(context.resources.getColor(R.color.white))
                }

            }
        }

        holder.itemView.setOnClickListener {
            itemOnClick(holder.adapterPosition)

            if (selectedPosition >= 0)
                notifyItemChanged(selectedPosition)
            selectedPosition = holder.adapterPosition
            notifyItemChanged(selectedPosition)
        }

    }

    override fun getItemCount(): Int = itemList.size
}