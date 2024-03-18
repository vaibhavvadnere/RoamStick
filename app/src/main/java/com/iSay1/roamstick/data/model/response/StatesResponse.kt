package com.iSay1.roamstick.data.model.response

data class StatesResponse(
    val message: String,
    val data: List<StatesData>,
    val success: Boolean
)