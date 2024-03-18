package com.iSay1.roamstick.data.model.response

import android.os.Parcel
import android.os.Parcelable

data class CareTakerDetail(
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
    val UpdatedBy: String?
):Parcelable {
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
        parcel.readString()
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
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CareTakerDetail> {
        override fun createFromParcel(parcel: Parcel): CareTakerDetail {
            return CareTakerDetail(parcel)
        }

        override fun newArray(size: Int): Array<CareTakerDetail?> {
            return arrayOfNulls(size)
        }
    }
}