package com.kidsboodle.teacher.data.model.forgotpwd

import com.google.gson.annotations.SerializedName

data class MobileVerifyRequest (
        @field:SerializedName("auth_provider")
        val authProvider: String = "teacher",

        @field:SerializedName("dial_code")
        val dialCode: String? = "+91",

        @field:SerializedName("phone_no")
        val mobileNo: String? = null,


)