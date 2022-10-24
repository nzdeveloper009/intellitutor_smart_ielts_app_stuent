package com.ielts.preparation.ui.grammarForIELTS.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ielts.preparation.databinding.TensesTypesItemsBinding
import com.ielts.preparation.ui.grammarForIELTS.data.PartsOfSpeechItems

class TensesTypesAdapter(
    private val itemList: List<PartsOfSpeechItems>,
    private val clickedItemText: String,
) : RecyclerView.Adapter<TensesTypesAdapter.ViewHolder>() {

    private var clickSecondAdapter: ClickSecondAdapter? = null

    class ViewHolder(
        val binding: TensesTypesItemsBinding,
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        binding = TensesTypesItemsBinding.inflate(
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
            (clickedItemText + " " + currentItem.text).also { tvTensesTypeCategoryName.text = it }
            holder.itemView.setOnClickListener {
                clickSecondAdapter?.onClickSecondAdapter((clickedItemText + " " + currentItem.text))
            }
        }
    }

    fun setOnAdapterSecondListener(clickSecondAdapter: ClickSecondAdapter) {
        this.clickSecondAdapter = clickSecondAdapter
    }

    override fun getItemCount(): Int = itemList.size
    interface ClickSecondAdapter {
        fun onClickSecondAdapter(clickedItemText: String)
    }
}