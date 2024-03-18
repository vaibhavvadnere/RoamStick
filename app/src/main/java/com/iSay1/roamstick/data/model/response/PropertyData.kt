package com.iSay1.roamstick.data.model.response

import android.os.Parcel
import android.os.Parcelable

@Suppress("UNREACHABLE_CODE")
data class PropertyData(
    val AddressLine1: String?,
    val AddressLine2: String?,
    val AddressLine3: String?,
    val CareTakerDetails: List<CareTakerDetail>?,
    val CityDistrictTown: String?,
    val CreatedAt: String?,
    val CreatedBy: String?,
    val Id: String?,
    val IsActive: String?,
    val Landmark: String?,
    val Latitude: String?,
    val Longitude: String?,
    val Name: String?,
    val Pincode: String?,
    val PropertyType: List<PropertyTypesList>?,
    val StateId: String?,
    val UpdatedAt: String?,
    val UpdatedBy: String?,
    val UserId: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        TODO("CareTakerDetails"),
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
        TODO("PropertyType"),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(AddressLine1)
        parcel.writeString(AddressLine2)
        parcel.writeString(AddressLine3)
        parcel.writeString(CityDistrictTown)
        parcel.writeString(CreatedAt)
        parcel.writeString(CreatedBy)
        parcel.writeString(Id)
        parcel.writeString(IsActive)
        parcel.writeString(Landmark)
        parcel.writeString(Latitude)
        parcel.writeString(Longitude)
        parcel.writeString(Name)
        parcel.writeString(Pincode)
        parcel.writeString(StateId)
        parcel.writeString(UpdatedAt)
        parcel.writeString(UpdatedBy)
        parcel.writeString(UserId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PropertyData> {
        override fun createFromParcel(parcel: Parcel): PropertyData {
            return PropertyData(parcel)
        }

        override fun newArray(size: Int): Array<PropertyData?> {
            return arrayOfNulls(size)
        }
    }
}
