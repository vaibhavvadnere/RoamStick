package com.iSay1.roamstick.data.model.response

data class PropertyDetailsResponse(
    val message: String,
    val data: List<PropertyData>,
    val success: Boolean
)