package com.kidsboodle.teacher.data.model.forgotpwd

import com.google.gson.annotations.SerializedName
import com.kidsboodle.teacher.data.model.login.Result
import com.kidsboodle.teacher.data.model.login.UserDetails

/*
response of mobile-verify api:
//{"request_status":1,
// "results":{"dial_code":"+91","Phone_number":"9330309158","otp":"MzI2NDI3"},
// "msg":"OTP sent, please check your mobile"}
*/




data class MobileVerifyResponse(

    @field:SerializedName("request_status")
    val requestStatus: Int? = null,

    @field:SerializedName("results")
    val resultMobile: ResultMobile? = null,

    @field:SerializedName("msg")
    val msg: String? = null
)
data class ResultMobile(

    @field:SerializedName("dial_code")
    val dialCode: String? = null,

    @field:SerializedName("Phone_number")
    val phoneNumber: String? = null,

    @field:SerializedName("otp")
    val otp: String? = null,

)
