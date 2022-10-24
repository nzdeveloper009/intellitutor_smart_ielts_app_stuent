package com.ielts.preparation.ui.grammarForIELTS.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ielts.preparation.R
import com.ielts.preparation.databinding.TensesItemsBinding
import com.ielts.preparation.ui.grammarForIELTS.data.PartsOfSpeechItems

class TensesAdapter(
    private val context: Context,
    private val itemList: List<PartsOfSpeechItems>,
    private val tensesChildCategory: ArrayList<PartsOfSpeechItems>,
    private val itemOnClick: (String, Int) -> Unit,
    private val clickFirstAdapter: ClickFirstAdapter,
    private var selectedPosition: Int = 0

) : RecyclerView.Adapter<TensesAdapter.ViewHolder>(), TensesTypesAdapter.ClickSecondAdapter {

    class ViewHolder(
        val binding: TensesItemsBinding,
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        binding = TensesItemsBinding.inflate(
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
            tvTensesTypeName.text = currentItem.text
            if (selectedPosition == position) {
                holder.itemView.isSelected = true
                btnExpand.rotation = 180F
                holder.binding.apply {
                    lyChildItems.isVisible = true
                    val childMemberAdapter =
                        TensesTypesAdapter(tensesChildCategory, currentItem.text)
                    childRecyclerview.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = childMemberAdapter
                    }
                    childMemberAdapter.setOnAdapterSecondListener(this@TensesAdapter)
                }
            } else {
                btnExpand.rotation = 0F
                holder.itemView.isSelected = false
                holder.binding.apply {
                    lyChildItems.isVisible = false
                }
            }

            holder.itemView.setOnClickListener {
                if (btnExpand.rotation == 0F) {
                    itemOnClick(currentItem.text, holder.adapterPosition)
                    if (selectedPosition >= 0)
                        notifyItemChanged(selectedPosition)
                    selectedPosition = holder.adapterPosition
                    notifyItemChanged(selectedPosition)
                } else if (btnExpand.rotation == 180F) {
                    btnExpand.rotation = 0F
                    lyChildItems.isVisible = false
                    itemOnClick(context.getString(R.string.tense), holder.adapterPosition)
                }
            }

        }

    }


    override fun getItemCount(): Int = itemList.size


    override fun onClickSecondAdapter(clickedItemText: String) {
        clickFirstAdapter.onClickFirstAdapter(clickedItemText)
    }

    interface ClickFirstAdapter {
        fun onClickFirstAdapter(clickedItemText: String)

    }
}