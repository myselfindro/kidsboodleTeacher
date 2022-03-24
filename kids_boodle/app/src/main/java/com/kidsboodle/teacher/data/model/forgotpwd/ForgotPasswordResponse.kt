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




data class ForgotPasswordResponse(

    @field:SerializedName("request_status")
    val requestStatus: Int? = null,


    @field:SerializedName("msg")
    val msg: String? = null
)
