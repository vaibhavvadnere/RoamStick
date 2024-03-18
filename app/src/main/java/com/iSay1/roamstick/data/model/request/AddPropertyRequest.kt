package com.iSay1.roamstick.data.model.request

import com.google.gson.annotations.SerializedName
import com.iSay1.roamstick.data.model.response.AmenitiesData
import com.iSay1.roamstick.data.model.response.ComplimentaryData

data class AddPropertyRequest(

    @SerializedName("UserId") var UserId: String? = null,
    @SerializedName("Name") var Name: String? = null,
    @SerializedName("AddressLine1") var AddressLine1: String? = null,
    @SerializedName("AddressLine2") var AddressLine2: String? = null,
    @SerializedName("AddressLine3") var AddressLine3: String? = null,
    @SerializedName("CityDistrictTown") var CityDistrictTown: String? = null,
    @SerializedName("Landmark") var Landmark: String? = null,
    @SerializedName("Pincode") var Pincode: String? = null,
    @SerializedName("StateId") var StateId: Int? = null,
    @SerializedName("CareTakerId") var CareTakerId: Int? = null,

    @SerializedName("IsActive") var IsActive: Boolean? = null,
    @SerializedName("Latitude") var Latitude: String? = null,
    @SerializedName("Longitude") var Longitude: String? = null,

    @SerializedName("CreatedBy") var CreatedBy: Int? = null,
    @SerializedName("CreatedAt") var CreatedAt: String? = null,
    @SerializedName("UpdatedBy") var UpdatedBy: Int? = null,
    @SerializedName("UpdatedAt") var UpdatedAt: String? = null,

    /*@SerializedName("NumberOfUnits") var NumberOfUnits: Int? = null,
    @SerializedName("ChargesUnitTypeId") var ChargesUnitTypeId: Int? = null,
    @SerializedName("ChargesPerUnit") var ChargesPerUnit: String? = null,
    @SerializedName("ValidFrom") var ValidFrom: String? = null,
    @SerializedName("ValidTo") var ValidTo: String? = null,
    @SerializedName("aminities") var aminities: ArrayList<AmenitiesData> = arrayListOf(),
    @SerializedName("complimentaries") var complimentaries: ArrayList<ComplimentaryData> = arrayListOf()*/
)
