package com.iSay1.roamstick.data.model.response

data class PropertyTypesDetailsResponse(
    val message: String,
    val data: List<PropertyTypesList>,
    val success: Boolean
)