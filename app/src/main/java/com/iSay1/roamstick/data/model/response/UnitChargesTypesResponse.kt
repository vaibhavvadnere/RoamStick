package com.iSay1.roamstick.data.model.response

data class UnitChargesTypesResponse(
    val message: String,
    val success: Boolean,
    val data: List<UnitChargesTypeData>
)