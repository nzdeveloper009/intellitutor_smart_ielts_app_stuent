package com.ielts.preparation.ui.speaking.speakingTests.data

import android.os.Parcel
import android.os.Parcelable

data class SpeakingTestItem(
    val answer: String?,
    val audio: String?,
    val id: Int,
    val is_favorite: Int,
    val question: String?,
    val should_say: String?,
    val vocab: String?,
    val your_answer: String?,
    val your_audio: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(answer)
        parcel.writeString(audio)
        parcel.writeInt(id)
        parcel.writeInt(is_favorite)
        parcel.writeString(question)
        parcel.writeString(should_say)
        parcel.writeString(vocab)
        parcel.writeString(your_audio)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SpeakingTestItem> {
        override fun createFromParcel(parcel: Parcel): SpeakingTestItem {
            return SpeakingTestItem(parcel)
        }

        override fun newArray(size: Int): Array<SpeakingTestItem?> {
            return arrayOfNulls(size)
        }
    }
}