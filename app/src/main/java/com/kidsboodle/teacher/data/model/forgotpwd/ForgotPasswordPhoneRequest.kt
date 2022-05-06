package com.kidsboodle.teacher.data.model.forgotpwd

import com.google.gson.annotations.SerializedName



data class ForgotPasswordPhoneRequest (

    @field:SerializedName("auth_provider")
    val authProvider: String = "teacher",

    @field:SerializedName("phone")
    val phone: String? = null,

    @field:SerializedName("new_password")
    val newPassword: String? = null,

    @field:SerializedName("confirm_password")
    val confirmPassword: String? = null,
)