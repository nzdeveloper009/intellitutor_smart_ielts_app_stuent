package com.ielts.preparation.ui.writing.writingTasks.adapter

import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ielts.preparation.databinding.TestsMainTopicItemsBinding
import com.ielts.preparation.ui.writing.writingTasks.data.WritingQuestionsItem
import com.ielts.preparation.ui.writing.writingTasks.data.WritingTask1Item

class WritingTasks1Adapter(
    private val questionList: Array<WritingQuestionsItem>,
    private val answersList: Array<WritingTask1Item>,
    private val testCategory: String,
    val itemOnClick: (
        question: Spanned,
        questionImageName: String,
        answer:String
    ) -> Unit
) : RecyclerView.Adapter<WritingTasks1Adapter.ViewHolder>() {
    class ViewHolder(
        val binding: TestsMainTopicItemsBinding
    ): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        binding = TestsMainTopicItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = questionList[position]
        val titleText = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
            Html.fromHtml(currentItem.question, Html.FROM_HTML_MODE_LEGACY)
        }else{
            Html.fromHtml(currentItem.question)
        }
        holder.binding.apply {
            "${position+1}.".also { tvItemNo.text = it }
            answersList.forEach {answer->
                if (answer.question_id == currentItem.question_id){
                    tvTestCategory.text = answer.type_detail
                    tvTestType.text = testCategory
                    holder.itemView.setOnClickListener {
                        itemOnClick(
                            titleText,
                            currentItem.image,
                            answer.answer
                        )
                    }
                }
            }
            tvTitleTest.text = titleText
            
        }

    }

    override fun getItemCount(): Int = questionList.size
}