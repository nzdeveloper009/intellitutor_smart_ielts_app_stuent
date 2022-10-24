package com.ielts.preparation.ui.ieltsTips.data

import android.os.Parcel
import android.os.Parcelable

data class IeltsTipsItems (
    val tipCategory : String?,
    val tipTitle : String?,
    val tipSource : String?,
    val tipContent: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(tipCategory)
        parcel.writeString(tipTitle)
        parcel.writeString(tipSource)
        parcel.writeString(tipContent)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<IeltsTipsItems> {
        override fun createFromParcel(parcel: Parcel): IeltsTipsItems {
            return IeltsTipsItems(parcel)
        }

        override fun newArray(size: Int): Array<IeltsTipsItems?> {
            return arrayOfNulls(size)
        }
    }
}