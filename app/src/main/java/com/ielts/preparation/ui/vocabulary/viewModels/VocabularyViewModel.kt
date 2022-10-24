package com.ielts.preparation.ui.vocabulary.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ielts.preparation.ui.vocabulary.data.VocabularyItems
import kotlinx.coroutines.launch
import org.json.JSONArray

class VocabularyViewModel(
    var vocabJson : String? = ""
) : ViewModel(){
    val vocabularyItems = getVocabularyList()
    val wordsCounted = getFilteredWords()
    val topicList = getFilteredTopics()
    init{
        viewModelScope.launch {
            vocabularyItems
            topicList
            wordsCounted
        }
    }


    private fun getVocabularyList(): MutableList<VocabularyItems> {
        val array = JSONArray(vocabJson)
        val vocabularyList = mutableListOf<VocabularyItems>()
        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            val topic = obj.getString("topic")
            val example = obj.getString("example")
            val meaning = obj.getString("meaning")
            val pronounUK = obj.getString("pronunUK")
            val pronounUS = obj.getString("pronunUS")
            val word = obj.getString("word")
            val wordType = obj.getString("wordType")
            vocabularyList.add(
                VocabularyItems(
                    topic,
                    example,
                    meaning,
                    pronounUK,
                    pronounUS,
                    word,
                    wordType
                )
            )
        }
        return vocabularyList
    }

    private fun getTopicList(): MutableList<String> {
        val array = JSONArray(vocabJson)
        val topicList = mutableListOf<String>()
        for (i in 0 until array.length()) {
            val jsonObject = array.getJSONObject(i)
            val topic = jsonObject.getString("topic")
            topicList += topic
        }
        return topicList
    }

    private fun getFilteredTopics(): ArrayList<String> {
        val topicArray : ArrayList<String> = ArrayList()
        getTopicList().forEach {
            if (!topicArray.contains(it)) {
                topicArray.add(it)
            }
        }
        return topicArray
    }


    private fun getFilteredWords(): ArrayList<Int> {
        val wordArray: ArrayList<Int> = ArrayList()
        getFilteredTopics().forEach { names ->
            wordArray.add(getTopicList().count {
                it == names
            })
        }
        return wordArray
    }
}