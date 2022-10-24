package com.ielts.preparation.ui.vocabulary.adapters

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.ielts.preparation.R
import com.ielts.preparation.databinding.VocabularyWordItemsBinding
import com.ielts.preparation.ui.vocabulary.data.VocabularyItems
import com.ielts.preparation.ui.vocabulary.fragments.VocabularyTopicsFragment
import java.util.*


class VocabularyWordAdapter(
    val context: Context,
    private val wordList: List<VocabularyItems>,
    val itemOnClick: (VocabularyItems) -> Unit,
    val btnTranslateOnClick: (String?) -> Unit

) : RecyclerView.Adapter<VocabularyWordAdapter.ViewHolder>() {

    class ViewHolder(
        val binding: VocabularyWordItemsBinding,
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder = ViewHolder(
        binding = VocabularyWordItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentWord = wordList[position]
        holder.binding.apply {

            val uppercaseTopic = currentWord.topic?.let { getCapsSentences(it) }

            tvTitleTopicName.text = uppercaseTopic

            "${position + 1}/${wordList.size}".also {
                tvWordPosition.text = it
            }

            val wordInLowercase = currentWord.word
            val wordInUppercase =
                wordInLowercase?.substring(0, 1)?.uppercase() + wordInLowercase?.substring(1)

            tvWordName.text = wordInUppercase

            "(${currentWord.wordType})".also { tvPartOfSpeech.text = it }
            tvPronounUK.text = currentWord.pronounUK
            tvPronounUS.text = currentWord.pronounUS
            btnMeaning.setOnClickListener {
                itemOnClick(currentWord)
            }
            btnTranslate.setOnClickListener {
                btnTranslateOnClick(currentWord.word)
            }
            btnWebSearch.setOnClickListener {
                val intent = Intent(Intent.ACTION_WEB_SEARCH)
                intent.putExtra(SearchManager.QUERY, "define ${currentWord.word}")
                context.startActivity(intent)
            }

        }
    }


    private fun getCapsSentences(tagName: String): String {
        val splits = tagName.lowercase().split(" ").toTypedArray()
        val sb = StringBuilder()
        for (i in splits.indices) {
            val eachWord = splits[i]
            if (i > 0 && eachWord.isNotEmpty()) {
                sb.append(" ")
            }
            val cap = (eachWord.substring(0, 1).uppercase()
                    + eachWord.substring(1))
            sb.append(cap)
        }
        return sb.toString()
    }

    override fun getItemCount(): Int = wordList.size
}