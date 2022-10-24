package com.ielts.preparation.data

import android.os.Parcel
import android.os.Parcelable

data class LessonItems(
    val content: String?,
    val mainTopic: String?,
    val remark: String?,
    val section: String?,
    val source: String?,
    val subTopic: String?,
    val title: String?,
    val topic: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
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
        parcel.writeString(content)
        parcel.writeString(mainTopic)
        parcel.writeString(remark)
        parcel.writeString(section)
        parcel.writeString(source)
        parcel.writeString(subTopic)
        parcel.writeString(title)
        parcel.writeString(topic)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LessonItems> {
        override fun createFromParcel(parcel: Parcel): LessonItems {
            return LessonItems(parcel)
        }

        override fun newArray(size: Int): Array<LessonItems?> {
            return arrayOfNulls(size)
        }
    }
}