package com.ielts.preparation.ui.grammarForIELTS.repo

import android.content.Context
import com.ielts.preparation.R
import com.ielts.preparation.ui.grammarForIELTS.data.PartsOfSpeechItems

class PartsOfSpeechRepo(
    val context: Context
){
    fun getPartsOfSpeech(): List<PartsOfSpeechItems> {
        return listOf(
            PartsOfSpeechItems(
                context.getString(R.string.noun)
            ),
            PartsOfSpeechItems(
                context.getString(R.string.pronoun)
            ),
            PartsOfSpeechItems(
                context.getString(R.string.verb)
            ),
            PartsOfSpeechItems(
                context.getString(R.string.adverb)
            ),
            PartsOfSpeechItems(
                context.getString(R.string.adjectives)
            ),
            PartsOfSpeechItems(
                context.getString(R.string.conjunction)
            ),
            PartsOfSpeechItems(
                context.getString(R.string.interjections)
            ),
            PartsOfSpeechItems(
                context.getString(R.string.prepositions)
            )
        )
    }

    fun getTenses(): List<PartsOfSpeechItems> {
        return listOf(
            PartsOfSpeechItems(
                context.getString(R.string.present)
            ),
            PartsOfSpeechItems(
                context.getString(R.string.past)
            ),
            PartsOfSpeechItems(
                context.getString(R.string.future)
            )
        )
    }

    fun getTensesTypes(): List<PartsOfSpeechItems> {
        return listOf(
            PartsOfSpeechItems(
                context.getString(R.string.simpletense)
            ),
            PartsOfSpeechItems(
                context.getString(R.string.continuous)
            ),
            PartsOfSpeechItems(
                context.getString(R.string.perfect_tense)
            ),
            PartsOfSpeechItems(
                context.getString(R.string.perfect_continuous_tense)
            )
        )
    }
}