package com.iSay1.roamstick.data.model.response

data class CreateUserResponse(
    val data: LoginData,
    val message: String,
    val success: Boolean
)