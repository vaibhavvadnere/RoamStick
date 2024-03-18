package com.iSay1.roamstick.data.model.response

data class PropertyTypesResponse(
    val `data`: List<PropertyTypeData>,
    val message: String,
    var selected: Boolean = false
)