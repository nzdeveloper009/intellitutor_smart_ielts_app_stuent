package com.ielts.preparation.ui.listening.listeningTests.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ielts.preparation.ui.listening.listeningTests.data.ListeningTestItem
import org.json.JSONArray

class ListeningTestViewModel (
    var listeningTestJson: String = ""
) : ViewModel() {
    val listeningTestItems = MutableLiveData<MutableList<ListeningTestItem>>()
    val listeningTestItemList = getListeningTestItems()

    init {
        listeningTestItems.value = listeningTestItemList
    }

    private fun getListeningTestItems(): MutableList<ListeningTestItem> {
        val jsonArray = JSONArray(listeningTestJson)
        val testItemList = mutableListOf<ListeningTestItem>()
        for (i in 0 until jsonArray.length()){
            val jsonObject = jsonArray.getJSONObject(i)
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

            testItemList.add(
                ListeningTestItem(
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
        return testItemList
    }
}