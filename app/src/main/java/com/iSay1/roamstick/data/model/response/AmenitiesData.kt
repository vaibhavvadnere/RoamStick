package com.iSay1.roamstick.data.model.response

import android.os.Parcel
import android.os.Parcelable

data class AmenitiesData(
    val Amenity: String?,
    val Description: String?,
    val Id: String?,
    val IsActive: String?,
    var selected: Boolean? = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Amenity)
        parcel.writeString(Description)
        parcel.writeString(Id)
        parcel.writeString(IsActive)
        parcel.writeValue(selected)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AmenitiesData> {
        override fun createFromParcel(parcel: Parcel): AmenitiesData {
            return AmenitiesData(parcel)
        }

        override fun newArray(size: Int): Array<AmenitiesData?> {
            return arrayOfNulls(size)
        }
    }
}