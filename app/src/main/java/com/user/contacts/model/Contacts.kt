package com.user.contacts.model

import com.google.gson.annotations.SerializedName

data class Contacts(
    val name: String,
    @SerializedName("phone_number") val phoneNumber: String,
    val timestamp: String

)