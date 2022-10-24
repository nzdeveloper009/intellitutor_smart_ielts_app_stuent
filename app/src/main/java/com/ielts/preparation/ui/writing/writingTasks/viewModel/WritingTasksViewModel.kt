package com.ielts.preparation.ui.writing.writingTasks.viewModel

import androidx.lifecycle.ViewModel
import com.ielts.preparation.ui.writing.writingTasks.data.WritingQuestionsItem
import com.ielts.preparation.ui.writing.writingTasks.data.WritingTask1Item
import com.ielts.preparation.ui.writing.writingTasks.data.WritingTask2Item
import org.json.JSONArray

class WritingTasksViewModel(
    private var writingQuestionJson: String? = "",
    private var writingTask1Json: String? = "",
    private var writingTask2Json: String? = ""
) : ViewModel() {

    var task1Question: Array<WritingQuestionsItem> = arrayOf()
    var task1Answers: Array<WritingTask1Item> = arrayOf()

    var task2Question: Array<WritingQuestionsItem> = arrayOf()
    var task2Answers: Array<WritingTask2Item> = arrayOf()

    init {
        getWritingQuestions()
        getWritingTask1()
        getFilteredTask1()
        getFilteredTask2()
    }

    private fun getWritingQuestions(): MutableList<WritingQuestionsItem> {
        val array = JSONArray(writingQuestionJson)
        val writingQuestion = mutableListOf<WritingQuestionsItem>()
        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            val image = obj.getString("image")
            val q_modelId = obj.getString("q_modelId")
            val q_recent = obj.getString("q_recent")
            val q_type = obj.getString("q_type")
            val question = obj.getString("question")
            val question_cate = obj.getString("question_cate")
            val question_id = obj.getString("question_id")

            writingQuestion.add(
                WritingQuestionsItem(
                    image,
                    q_modelId.toString().toInt(),
                    q_recent.toString().toInt(),
                    q_type.toString().toInt(),
                    question,
                    question_cate.toString().toInt(),
                    question_id.toString().toInt()
                )
            )
        }
        return writingQuestion
    }

    private fun getWritingTask1(): MutableList<WritingTask1Item> {
        val array = JSONArray(writingTask1Json)
        val writingTask1Answers = mutableListOf<WritingTask1Item>()
        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            val answer = obj.getString("answer")
            val graph_cate = obj.getString("graph_cate")
            val graph_id = obj.getString("graph_id")
            val question_id = obj.getString("question_id")
            val type_detail = obj.getString("type_detail")
            val vocab = obj.getString("vocab")

            writingTask1Answers.add(
                WritingTask1Item(
                    answer,
                    graph_cate.toString().toInt(),
                    graph_id.toString().toInt(),
                    question_id.toString().toInt(),
                    type_detail,
                    vocab
                )
            )
        }
        return writingTask1Answers
    }

    private fun getFilteredTask1() {

        getWritingQuestions().forEach { question ->
            getWritingTask1().forEach { answerDetails ->
                if (question.question_id == answerDetails.question_id) {
                    task1Question += arrayOf(
                        WritingQuestionsItem(
                            question.image,
                            question.q_modelId.toString().toInt(),
                            question.q_recent.toString().toInt(),
                            question.q_type.toString().toInt(),
                            question.question,
                            question.question_cate.toString().toInt(),
                            question.question_id.toString().toInt()
                        )
                    )
                    task1Answers += arrayOf(
                        WritingTask1Item(
                            answerDetails.answer,
                            answerDetails.graph_cate.toString().toInt(),
                            answerDetails.graph_id.toString().toInt(),
                            answerDetails.question_id.toString().toInt(),
                            answerDetails.type_detail,
                            answerDetails.vocab
                        )
                    )
                }
            }
        }
    }

    private fun getWritingTask2(): MutableList<WritingTask2Item> {
        val array = JSONArray(writingTask2Json)
        val writingTask2Answers = mutableListOf<WritingTask2Item>()
        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            val answer = obj.getString("answer")
            val es_category = obj.getString("es_category")
            val es_id = obj.getString("es_id")
            val question_id = obj.getString("question_id")
            val type_detail = obj.getString("type_detail")
            val vocab = obj.getString("vocab")

            writingTask2Answers.add(
                WritingTask2Item(
                    answer,
                    es_category.toString().toInt(),
                    es_id.toString().toInt(),
                    question_id.toString().toInt(),
                    type_detail,
                    vocab
                )
            )
        }
        return writingTask2Answers
    }

    private fun getFilteredTask2() {
        getWritingQuestions().forEach { question ->
            getWritingTask2().forEach { answerDetails ->
                if (question.question_id == answerDetails.question_id) {
                    task2Question += arrayOf(
                        WritingQuestionsItem(
                            question.image,
                            question.q_modelId.toString().toInt(),
                            question.q_recent.toString().toInt(),
                            question.q_type.toString().toInt(),
                            question.question,
                            question.question_cate.toString().toInt(),
                            question.question_id.toString().toInt()
                        )
                    )
                    task2Answers += arrayOf(
                        WritingTask2Item(
                            answerDetails.answer,
                            answerDetails.es_category.toString().toInt(),
                            answerDetails.es_id.toString().toInt(),
                            answerDetails.question_id.toString().toInt(),
                            answerDetails.type_detail,
                            answerDetails.vocab
                        )
                    )
                }
            }
        }
    }
}