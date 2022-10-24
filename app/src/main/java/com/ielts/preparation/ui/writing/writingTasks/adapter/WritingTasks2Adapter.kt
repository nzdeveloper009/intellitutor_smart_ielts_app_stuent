package com.ielts.preparation.ui.writing.writingTasks.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ielts.preparation.databinding.TestsMainTopicItemsBinding
import com.ielts.preparation.ui.writing.writingTasks.data.WritingQuestionsItem
import com.ielts.preparation.ui.writing.writingTasks.data.WritingTask2Item

class WritingTasks2Adapter(
    private val questionList: Array<WritingQuestionsItem>,
    private val answersList: Array<WritingTask2Item>,
    private val testCategory: String,
    val itemOnClick: (
        question: String,
        answer: String
    ) -> Unit
) : RecyclerView.Adapter<WritingTasks2Adapter.ViewHolder>() {
    class ViewHolder(
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
        val currentItem = questionList[position]
        val questionTitleText =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                Html.fromHtml(currentItem.question, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(currentItem.question)
            }
        holder.binding.apply {
            "${position + 1}.".also { tvItemNo.text = it }
            answersList.forEach { answer ->
                if (answer.question_id == currentItem.question_id) {
                    tvTestCategory.text = answer.type_detail
                    tvTestCategory
                    tvTestType.text = testCategory
                }
                holder.itemView.setOnClickListener {
                    itemOnClick(
                        questionTitleText.toString(),
                        answer.answer
                    )
                }
            }
            tvTitleTest.text = questionTitleText
        }
    }

    override fun getItemCount(): Int = questionList.size
}