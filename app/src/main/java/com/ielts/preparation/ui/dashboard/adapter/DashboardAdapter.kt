package com.ielts.preparation.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ielts.preparation.ui.dashboard.data.DashboardItems
import com.ielts.preparation.databinding.DashboardCardviewItemsBinding

class DashboardAdapter(
    private val itemList: List<DashboardItems>,
    val itemOnClick: (Int) -> Unit
) : RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {
    class ViewHolder(
        val binding: DashboardCardviewItemsBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DashboardCardviewItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.binding.apply {
            imageViewItemImage.setImageDrawable(currentItem.itemImage)
            tvItemName.text = currentItem.itemText
            tvLessonOrTest.text = currentItem.cardType
            cvItemsMainBackground.setCardBackgroundColor(currentItem.color)
        }
        holder.itemView.setOnClickListener {
            itemOnClick(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int = itemList.size
}