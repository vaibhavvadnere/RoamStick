package com.iSay1.roamstick.data.model.request

import com.google.gson.annotations.SerializedName
import com.iSay1.roamstick.data.model.response.AmenitiesData
import com.iSay1.roamstick.data.model.response.ComplimentaryData

data class AddPropertyTypesRequest(

    @SerializedName("ValidTo") var ValidTo: String? = null,
    @SerializedName("CreatedBy") var CreatedBy: Int? = null,
    @SerializedName("IsActive") var IsActive: Boolean? = null,
    @SerializedName("PropertyId") var PropertyId: Int? = null,
    @SerializedName("CreatedAt") var CreatedAt: String? = null,
    @SerializedName("ValidFrom") var ValidFrom: String? = null,
    @SerializedName("NumberOfUnits") var NumberOfUnits: Int? = null,
    @SerializedName("ChargesPerUnit") var ChargesPerUnit: String? = null,
    @SerializedName("PropertyTypeId") var PropertyTypeId: String? = null,
    @SerializedName("ChargesUnitTypeId") var ChargesUnitTypeId: Int? = null,
    @SerializedName("aminities") var aminities: ArrayList<AmenitiesData> = arrayListOf(),
    @SerializedName("complimentaries") var complimentaries: ArrayList<ComplimentaryData> = arrayListOf()
)
