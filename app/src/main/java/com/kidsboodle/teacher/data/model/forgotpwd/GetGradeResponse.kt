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




data class GetGradeResponse(
    @field:SerializedName("count")
    val count: Int? = null,

    @field:SerializedName("request_status")
    val requestStatus: Int? = null,

    @field:SerializedName("msg")
    val msg: String? = null,

    @field:SerializedName("results")
    val result: List<GradeResultItems?>? = null
)

data class GradeResultItems(



    @field:SerializedName("student_name")
    val student_name: String? = null,


    @field:SerializedName("id")
    val id: Int? = null,


    @field:SerializedName("total_marks_obtained")
    val total_marks_obtained: String? = "",

    @field:SerializedName("is_active")
    val is_active: Boolean? = false
)

