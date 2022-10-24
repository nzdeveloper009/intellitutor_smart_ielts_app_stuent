package com.ielts.preparation.viewModel.lessonViewModel

import androidx.lifecycle.ViewModel
import com.ielts.preparation.data.LessonItems
import org.json.JSONArray

class LessonViewModel(
    private var lessonJson: String? = ""
) : ViewModel() {
    val filterReadingItems = getFilteredReadingItems()
    val filterWritingItems = getFilteredWritingItems()
    val filterSpeakingItems = getFilteredSpeakingItems()
    val filterListeningItems = getFilteredListeningItems()

    init {
        getLessonItemList()
        filterReadingItems
        filterWritingItems
        filterSpeakingItems
        filterListeningItems
    }

    private fun getLessonItemList(): MutableList<LessonItems> {
        val array = JSONArray(lessonJson)
        val lessonItemList = mutableListOf<LessonItems>()
        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            val content = obj.getString("content")
            val mainTopic = obj.getString("main_topic")
            val remark = obj.getString("remark")
            val section = obj.getString("section")
            val source = obj.getString("source")
            val subTopic = obj.getString("sub_topic")
            val title = obj.getString("title")
            val topic = obj.getString("topic")
            lessonItemList.add(
                LessonItems(
                    content,
                    mainTopic,
                    remark,
                    section,
                    source,
                    subTopic,
                    title,
                    topic
                )
            )
        }
        return lessonItemList
    }

    private fun getFilteredReadingItems(): MutableList<LessonItems> {
        val filteredReadingItemsList = mutableListOf<LessonItems>()
        getLessonItemList().forEach {
            if (it.section == "Reading") {
                filteredReadingItemsList.add(
                    LessonItems(
                        it.content,
                        it.mainTopic,
                        it.remark,
                        it.section,
                        it.source,
                        it.subTopic,
                        it.title,
                        it.topic
                    )
                )
            }
        }
        return filteredReadingItemsList
    }

    private fun getFilteredWritingItems(): MutableList<LessonItems> {
        val filteredWritingItems = mutableListOf<LessonItems>()
        getLessonItemList().forEach {
            if (it.section == "Writing") {
                filteredWritingItems.add(
                    LessonItems(
                        it.content,
                        it.mainTopic,
                        it.remark,
                        it.section,
                        it.source,
                        it.subTopic,
                        it.title,
                        it.topic
                    )
                )
            }
        }
        return filteredWritingItems
    }

    private fun getFilteredSpeakingItems(): MutableList<LessonItems> {
        val filteredSpeakingItems = mutableListOf<LessonItems>()
        getLessonItemList().forEach {
            if (it.section == "Speaking") {
                filteredSpeakingItems.add(
                    LessonItems(
                        it.content,
                        it.mainTopic,
                        it.remark,
                        it.section,
                        it.source,
                        it.subTopic,
                        it.title,
                        it.topic
                    )
                )
            }
        }
        return filteredSpeakingItems
    }
    private fun getFilteredListeningItems(): MutableList<LessonItems> {
        val filteredListeningItems = mutableListOf<LessonItems>()
        getLessonItemList().forEach {
            if (it.section == "Listening") {
                filteredListeningItems.add(
                    LessonItems(
                        it.content,
                        it.mainTopic,
                        it.remark,
                        it.section,
                        it.source,
                        it.subTopic,
                        it.title,
                        it.topic
                    )
                )
            }
        }
        return filteredListeningItems
    }
}