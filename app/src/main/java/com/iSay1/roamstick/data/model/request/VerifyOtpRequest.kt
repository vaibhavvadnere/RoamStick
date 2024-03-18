package com.iSay1.roamstick.data.model.request

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VerifyOtpRequest(

    @SerializedName("email") @Expose var email: String? = null,
    @SerializedName("otp") @Expose var otp: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
        parcel.writeString(otp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VerifyOtpRequest> {
        override fun createFromParcel(parcel: Parcel): VerifyOtpRequest {
            return VerifyOtpRequest(parcel)
        }

        override fun newArray(size: Int): Array<VerifyOtpRequest?> {
            return arrayOfNulls(size)
        }
    }
}
