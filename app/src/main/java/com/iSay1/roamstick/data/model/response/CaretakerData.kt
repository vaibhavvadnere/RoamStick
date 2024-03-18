package com.iSay1.roamstick.data.model.response

import android.os.Parcel
import android.os.Parcelable

data class CaretakerData(
    val Address: String?,
    val CityDistrictTown: String?,
    val CountryId: String?,
    val CreatedAt: String?,
    val CreatedBy: String?,
    val Email: String?,
    val FirstName: String?,
    val Id: String?,
    val Landmark: String?,
    val LastName: String?,
    val Locality: String?,
    val MobileNumber: String?,
    val PinCode: String?,
    val StateId: String?,
    val UpdatedAt: String?,
    val UpdatedBy: String?,
    var Selected: Boolean = false
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
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Address)
        parcel.writeString(CityDistrictTown)
        parcel.writeString(CountryId)
        parcel.writeString(CreatedAt)
        parcel.writeString(CreatedBy)
        parcel.writeString(Email)
        parcel.writeString(FirstName)
        parcel.writeString(Id)
        parcel.writeString(Landmark)
        parcel.writeString(LastName)
        parcel.writeString(Locality)
        parcel.writeString(MobileNumber)
        parcel.writeString(PinCode)
        parcel.writeString(StateId)
        parcel.writeString(UpdatedAt)
        parcel.writeString(UpdatedBy)
        parcel.writeByte(if (Selected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CaretakerData> {
        override fun createFromParcel(parcel: Parcel): CaretakerData {
            return CaretakerData(parcel)
        }

        override fun newArray(size: Int): Array<CaretakerData?> {
            return arrayOfNulls(size)
        }
    }
}
