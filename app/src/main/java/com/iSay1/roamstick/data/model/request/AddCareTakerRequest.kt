package com.iSay1.roamstick.data.model.request

import com.google.gson.annotations.SerializedName
import com.iSay1.roamstick.data.model.response.AmenitiesData
import com.iSay1.roamstick.data.model.response.ComplimentaryData

data class AddCareTakerRequest(

    @SerializedName("FirstName") var FirstName: String? = null,
    @SerializedName("LastName") var LastName: String? = null,
    @SerializedName("Email") var Email: String? = null,
    @SerializedName("CityDistrictTown") var CityDistrictTown: String? = null,
    @SerializedName("Address") var Address: String? = null,
    @SerializedName("MobileNumber") var MobileNumber: String? = null,
    @SerializedName("Landmark") var Landmark: String? = null,
    @SerializedName("Locality") var Locality: String? = null,
    @SerializedName("PinCode") var PinCode: String? = null,
    @SerializedName("StateId") var StateId: String? = null,
    @SerializedName("CountryId") var CountryId: String? = null,
    @SerializedName("CreatedBy") var CreatedBy: String? = null,
    @SerializedName("CreatedAt") var CreatedAt: String? = null,
    @SerializedName("UpdatedBy") var UpdatedBy: String? = null,
    @SerializedName("UpdatedAt") var UpdatedAt: String? = null,
)
