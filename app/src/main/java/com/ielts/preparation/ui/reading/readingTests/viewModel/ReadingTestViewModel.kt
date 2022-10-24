package com.ielts.preparation.ui.reading.readingTests.viewModel

import androidx.lifecycle.ViewModel
import com.ielts.preparation.ui.reading.readingTests.data.ReadingTestsItems
import org.json.JSONArray

class ReadingTestViewModel(
    var readingTestJson: String = ""
) : ViewModel() {
    val readingTestItem = getReadingTestItemList()
    init {
        readingTestItem
    }

    private fun getReadingTestItemList(): MutableList<ReadingTestsItems> {
        val array = JSONArray(readingTestJson)
        val lessonItemList = mutableListOf<ReadingTestsItems>()
        for (i in 0 until array.length()) {
            val jsonObject = array.getJSONObject(i)
            val answerDetail = jsonObject.getString("answerDetail")
            val answerList = jsonObject.getString("answerList")
            val answerLocation = jsonObject.getString("answerLocation")
            val extra = jsonObject.getString("extra")
            val paragraph = jsonObject.getString("paragraph")
            val question = jsonObject.getString("question")
            val questionType = jsonObject.getString("questionType")
            val title = jsonObject.getString("title")
            val topic = jsonObject.getString("topic")
            val type = jsonObject.getString("type")

            lessonItemList.add(
                ReadingTestsItems(
                    answerDetail,
                    answerList,
                    answerLocation,
                    extra,
                    paragraph,
                    question,
                    questionType,
                    title,
                    topic,
                    type
                )
            )
        }
        return lessonItemList
    }

}