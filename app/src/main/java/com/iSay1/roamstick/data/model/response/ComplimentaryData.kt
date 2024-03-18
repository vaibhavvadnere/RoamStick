package com.iSay1.roamstick.data.model.response

import android.os.Parcel
import android.os.Parcelable

data class ComplimentaryData(
    val Complimentory: String?,
    val Id: String?,
    val IsActive: String?,
    var selected: Boolean? = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Complimentory)
        parcel.writeString(Id)
        parcel.writeString(IsActive)
        parcel.writeValue(selected)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ComplimentaryData> {
        override fun createFromParcel(parcel: Parcel): ComplimentaryData {
            return ComplimentaryData(parcel)
        }

        override fun newArray(size: Int): Array<ComplimentaryData?> {
            return arrayOfNulls(size)
        }
    }
}