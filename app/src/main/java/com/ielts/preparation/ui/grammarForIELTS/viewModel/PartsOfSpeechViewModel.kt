package com.ielts.preparation.ui.grammarForIELTS.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ielts.preparation.ui.grammarForIELTS.data.PartsOfSpeechItems

class PartsOfSpeechViewModel : ViewModel() {
    val partsOfSpeechItems = MutableLiveData<ArrayList<PartsOfSpeechItems>>()
    var itemList = arrayListOf<PartsOfSpeechItems>()

    fun addItems(POSList: List<PartsOfSpeechItems>){
        if (itemList.isNullOrEmpty()){
            for (item in POSList) {
                itemList.add(item)
            }
        }
        partsOfSpeechItems.value = itemList
    } 
}