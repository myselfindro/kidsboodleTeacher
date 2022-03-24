package com.kidsboodle.teacher.data.model.login

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @field:SerializedName("auth_provider")
    val authProvider: String = "schooluser",

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("password")
    val password: String? = null
)