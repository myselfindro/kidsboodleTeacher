package com.kidsboodle.teacher.data.model.forgotpwd

import com.google.gson.annotations.SerializedName
import com.kidsboodle.teacher.data.model.login.Result
import com.kidsboodle.teacher.data.model.login.UserDetails


/*
{"request_status":1,"results":{"email_id":"samanwitachatterjee@gmail.com","otp":"NDQ2ODA3"},
"msg":"OTP sent, please check your mailid"}
 */



data class EmailVerifyResponse(

    @field:SerializedName("request_status")
    val requestStatus: Int? = null,

    @field:SerializedName("results")
    val resultEmailVerify: ResultEmailVerify? = null,

    @field:SerializedName("msg")
    val msg: String? = null
)
data class ResultEmailVerify(

    @field:SerializedName("email_id")
    val emailId: String? = null,


    @field:SerializedName("otp")
    val otp: String? = null,

)
