package com.kidsboodle.teacher.data.model.forgotpwd

import com.google.gson.annotations.SerializedName

data class EmailVerifyRequest (
        @field:SerializedName("auth_provider")
        val authProvider: String = "teacher",

        @field:SerializedName("email_id")
        val emailId: String? = null,


)