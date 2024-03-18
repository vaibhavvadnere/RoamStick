package com.iSay1.roamstick.data.model.response

import android.os.Parcel
import android.os.Parcelable

data class LoginData(
    val CreatedAt: String?,
    val DateOfBirth: String?,
    val Email: String?,
    val FirstName: String?,
    val Id: String?,
    val IsActive: String?,
    val LastName: String?,
    val Password: String?,
    val UpdatedAt: String?,
    val UserType: String?
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
        parcel.writeString(CreatedAt)
        parcel.writeString(DateOfBirth)
        parcel.writeString(Email)
        parcel.writeString(FirstName)
        parcel.writeString(Id)
        parcel.writeString(IsActive)
        parcel.writeString(LastName)
        parcel.writeString(Password)
        parcel.writeString(UpdatedAt)
        parcel.writeString(UserType)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoginData> {
        override fun createFromParcel(parcel: Parcel): LoginData {
            return LoginData(parcel)
        }

        override fun newArray(size: Int): Array<LoginData?> {
            return arrayOfNulls(size)
        }
    }
}