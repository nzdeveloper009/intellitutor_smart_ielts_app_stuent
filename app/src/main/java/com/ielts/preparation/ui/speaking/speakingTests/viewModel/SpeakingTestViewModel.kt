package com.ielts.preparation.ui.speaking.speakingTests.viewModel

import androidx.lifecycle.ViewModel
import com.ielts.preparation.ui.speaking.speakingTests.data.SpeakingTestItem
import org.json.JSONArray

class SpeakingTestViewModel(
   private var speakingTestJson : String? = ""
) : ViewModel() {
    val speakingItemList = getSpeakingTestItemList()

    init {
        speakingItemList
    }

    private fun getSpeakingTestItemList(): MutableList<SpeakingTestItem> {
        val jsonArray = JSONArray(speakingTestJson)
        val testItemList = mutableListOf<SpeakingTestItem>()
        for (i in 0 until jsonArray.length()){
            val jsonObject = jsonArray.getJSONObject(i)
            val answer = jsonObject.getString("answer")
            val audio= jsonObject.getString("audio")
            val id= jsonObject.getString("id").toInt()
            val is_favorite= jsonObject.getString("is_favorite").toInt()
            val question= jsonObject.getString("question")
            val should_say= jsonObject.getString("should_say")
            val vocab= jsonObject.getString("vocab")
            val your_answer= jsonObject.getString("your_answer")
            val your_audio= jsonObject.getString("your_audio")

            testItemList.add(
                SpeakingTestItem(
                    answer,
                    audio,
                    id,
                    is_favorite,
                    question,
                    should_say,
                    vocab,
                    your_answer,
                    your_audio
                )
            )
        }
        return testItemList
    }
}