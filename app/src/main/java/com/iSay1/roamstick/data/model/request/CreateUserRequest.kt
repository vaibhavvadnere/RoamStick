package com.iSay1.roamstick.data.model.request

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CreateUserRequest(

    @SerializedName("email") @Expose var email: String? = null,
    @SerializedName("firstName") @Expose var firstName: String? = null,
    @SerializedName("lastName") @Expose var lastName: String? = null,
    @SerializedName("dateOfBirth") @Expose var dateOfBirth: String? = null,
    @SerializedName("password") @Expose var password: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(dateOfBirth)
        parcel.writeString(password)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CreateUserRequest> {
        override fun createFromParcel(parcel: Parcel): CreateUserRequest {
            return CreateUserRequest(parcel)
        }

        override fun newArray(size: Int): Array<CreateUserRequest?> {
            return arrayOfNulls(size)
        }
    }
}
