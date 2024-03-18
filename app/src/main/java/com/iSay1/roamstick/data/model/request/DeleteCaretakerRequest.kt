package com.iSay1.roamstick.data.model.request

import com.google.gson.annotations.SerializedName
import com.iSay1.roamstick.data.model.response.AmenitiesData
import com.iSay1.roamstick.data.model.response.ComplimentaryData

data class DeleteCaretakerRequest(
    @SerializedName("CareTakerId") var CareTakerId: Int? = null,
    @SerializedName("PropertyId") var PropertyId: Int? = null
)
