package com.ielts.preparation.ui.reading.readingTests.data

import android.os.Parcel
import android.os.Parcelable

data class ReadingTestsItems(
    val answerDetail: String?,
    val answerList: String?,
    val answerLocation: String?,
    val extra: String?,
    val paragraph: String?,
    val question: String?,
    val questionType: String?,
    val title: String?,
    val topic: String?,
    val type: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
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
        parcel.writeString(answerDetail)
        parcel.writeString(answerList)
        parcel.writeString(answerLocation)
        parcel.writeString(extra)
        parcel.writeString(paragraph)
        parcel.writeString(question)
        parcel.writeString(questionType)
        parcel.writeString(title)
        parcel.writeString(topic)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ReadingTestsItems> {
        override fun createFromParcel(parcel: Parcel): ReadingTestsItems {
            return ReadingTestsItems(parcel)
        }

        override fun newArray(size: Int): Array<ReadingTestsItems?> {
            return arrayOfNulls(size)
        }
    }
}