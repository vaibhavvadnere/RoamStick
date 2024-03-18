package com.iSay1.roamstick.data.model.response

data class BaseResponse(
    val message: String,
    val success: Boolean,
    val data: Any
)
