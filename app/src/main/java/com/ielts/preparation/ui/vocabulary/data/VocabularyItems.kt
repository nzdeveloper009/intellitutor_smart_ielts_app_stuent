package com.ielts.preparation.ui.vocabulary.data


import android.os.Parcel
import android.os.Parcelable

data class VocabularyItems(
    val topic: String?,
    val example: String?,
    val meaning: String?,
    val pronounUK: String?,
    val pronounUS: String?,
    val word: String?,
    val wordType: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(topic)
        parcel.writeString(example)
        parcel.writeString(meaning)
        parcel.writeString(pronounUK)
        parcel.writeString(pronounUS)
        parcel.writeString(word)
        parcel.writeString(wordType)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VocabularyItems> {
        override fun createFromParcel(parcel: Parcel): VocabularyItems {
            return VocabularyItems(parcel)
        }

        override fun newArray(size: Int): Array<VocabularyItems?> {
            return arrayOfNulls(size)
        }
    }
}