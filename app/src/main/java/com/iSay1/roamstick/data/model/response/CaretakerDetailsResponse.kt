package com.iSay1.roamstick.data.model.response

data class CaretakerDetailsResponse(
    val data: List<CaretakerData>,
    val message: String,
    val success: Boolean
)