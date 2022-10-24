package com.ielts.preparation.ui.ieltsTips.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ielts.preparation.R
import com.ielts.preparation.databinding.IeltsTipsHomeItemsBinding
import com.ielts.preparation.ui.ieltsTips.data.IeltsTipsItems

class IeltsTipsAdapter(
    val context : Context,
    private val tipsList: List<IeltsTipsItems>,
    val itemOnClick : (IeltsTipsItems) -> Unit
):RecyclerView.Adapter<IeltsTipsAdapter.ViewHolder>() {

    class ViewHolder(
        val binding: IeltsTipsHomeItemsBinding
    ): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        binding = IeltsTipsHomeItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem  = tipsList[position]
        holder.binding.apply {
            tvIeltsTipsNo.text = "${position+1}"
            tvTipsCategory.text = currentItem.tipCategory
            tvTipsTitleName.text = currentItem.tipTitle
        }
        holder.itemView.setOnClickListener {
            itemOnClick(currentItem)
        }
    }

    override fun getItemCount(): Int = tipsList.size
}