package com.ielts.preparation.ui.grammarForIELTS.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ielts.preparation.ui.dashboard.data.DashboardItems
import com.ielts.preparation.ui.grammarForIELTS.data.PartsOfSpeechItems

class GrammarViewModel: ViewModel(){
    val grammarItems =  MutableLiveData<ArrayList<DashboardItems>>()
    private val itemsList = arrayListOf<DashboardItems>()

    val partsOfSpeechItems = MutableLiveData<ArrayList<PartsOfSpeechItems>>()
    private var partsOfSpeechItemList = arrayListOf<PartsOfSpeechItems>()

    val tensesItems = mutableListOf<ArrayList<PartsOfSpeechItems>>()
    private var tensesItemList = arrayListOf<PartsOfSpeechItems>()


    val tensesTypeItems = mutableListOf<ArrayList<PartsOfSpeechItems>>()
    private var tensesTypeItemList = arrayListOf<PartsOfSpeechItems>()

    init {
        itemsList
        partsOfSpeechItemList
        tensesItemList
        tensesTypeItemList
    }

    fun addGrammarHomeItems(items: List<DashboardItems>){
        if (itemsList.isNullOrEmpty()){
            for (grammarItems in items) {
                itemsList.add(grammarItems)
            }
        }
        grammarItems.value = itemsList
    }

    fun addPartsOfSpeechItems(POSList: List<PartsOfSpeechItems>){
        if (partsOfSpeechItemList.isNullOrEmpty()){
            for (item in POSList) {
                partsOfSpeechItemList.add(item)
            }
        }
        partsOfSpeechItems.value = partsOfSpeechItemList
    }

    fun addTensesItems(POSList: List<PartsOfSpeechItems>){
        if (tensesItemList.isNullOrEmpty()){
            for (item in POSList) {
                tensesItemList.add(item)
            }
        }
        tensesItems.add(tensesItemList)
    }

    fun addTensesTypesItems(POSList: List<PartsOfSpeechItems>){
        if (tensesTypeItemList.isNullOrEmpty()){
            for (item in POSList) {
                tensesTypeItemList.add(item)
            }
        }
        tensesTypeItems.add(tensesTypeItemList)
    }
}