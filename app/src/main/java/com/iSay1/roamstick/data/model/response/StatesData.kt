package com.iSay1.roamstick.data.model.response

data class StatesData(
    val CountryId: String,
    val Id: String,
    val IsActive: String,
    val StateName: String,
    var selected: Boolean
)