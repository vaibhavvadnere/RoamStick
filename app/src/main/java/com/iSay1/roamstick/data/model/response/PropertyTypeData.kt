package com.iSay1.roamstick.data.model.response

data class PropertyTypeData(
    val CreatedAt: String,
    val CreatedBy: String,
    val Description: String,
    val Id: String,
    val IsActive: String,
    val TypeName: String,
    val UpdatedAt: String,
    val UpdatedBy: String,
    var selected: Boolean = false
)