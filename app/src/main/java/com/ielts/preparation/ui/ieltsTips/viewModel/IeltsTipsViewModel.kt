package com.ielts.preparation.ui.ieltsTips.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ielts.preparation.ui.ieltsTips.data.IeltsTipsItems
import kotlinx.coroutines.launch
import org.json.JSONArray

class IeltsTipsViewModel(
    var tipsJson: String? = ""
) : ViewModel() {
    val ieltsTips = getTipsList()

    init {
        viewModelScope.launch {
            ieltsTips
        }
    }

    private fun getTipsList(): MutableList<IeltsTipsItems> {
        val array = JSONArray(tipsJson)
        val ieltsTipsList = mutableListOf<IeltsTipsItems>()
        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            val tipCategory = obj.getString("tip_cate")
            val tipTitle = obj.getString("tip_title")
            val tipSource = obj.getString("tip_source")
            val tipContent = obj.getString("tip_content")
            ieltsTipsList.add(
                IeltsTipsItems(
                    tipCategory,
                    tipTitle,
                    tipSource,
                    tipContent
                )
            )
        }
        return ieltsTipsList
    }
}